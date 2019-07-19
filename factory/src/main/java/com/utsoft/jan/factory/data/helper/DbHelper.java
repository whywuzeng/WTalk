package com.utsoft.jan.factory.data.helper;

import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.ModelAdapter;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ITransaction;
import com.utsoft.jan.factory.model.db.AppDataBase;
import com.utsoft.jan.factory.model.db.Message;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2019/7/2.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.factory.data.helper
 */
public class DbHelper {

    private static final DbHelper instance;

    static {
        instance = new DbHelper();
    }

    private DbHelper(){

    }

    private final Map<Class<?>,Set<ChangedListener>> changedListebers = new HashMap<>();

    /**
     * 新增和修改统一方法
     */
    public static <Model extends BaseModel> void save(final Class<Model> aClass,
                                                      final Model... models) {
        FlowManager.getDatabase(AppDataBase.class)
                .beginTransactionAsync(new ITransaction() {
                    @Override
                    public void execute(DatabaseWrapper databaseWrapper) {
                        ModelAdapter<Model> adapter = FlowManager.getModelAdapter(aClass);
                        adapter.saveAll(Arrays.asList(models));
                        instance.notifySave(aClass, models);

                    }
                }).build().execute();
    }

    /**
     * 进行通知调用
     * @param aClass 通知类型
     * @param models 通知的model数组
     * @param <Model> 这个实例的泛型 extend BaseModel
     */
    private <Model extends BaseModel> void notifySave(Class<Model> aClass, Model[] models) {
        final Set<ChangedListener> listener = getListener(aClass);
        //调用 接口
        if (listener != null && listener.size() > 0) {
            for (ChangedListener item :
                    listener) {
                item.onDataSave(models);
            }
        }

        if (aClass.equals(Message.class))
        {
            UpdateSession((Message[])models);
        }
    }

    private void UpdateSession(Message... models) {
        //根据一個ID 要生成多個ID 一樣的session、然后在根据message是设置内容

    }

    private <Model extends BaseModel> Set<ChangedListener> getListener(Class<Model> aClass) {
        if (changedListebers.containsKey(aClass))
        {
            Set<ChangedListener> listeners = changedListebers.get(aClass);
            return listeners;
        }
        return null;
    }

    /**
     * 增加监听
      * @param tClass
     * @param listener
     * @param <Model>
     */
    public static <Model extends BaseModel> void addChangeListener(final Class<Model> tClass,
                                                             ChangedListener<Model> listener) {
        Set<ChangedListener> changedListeners = instance.changedListebers.get(tClass);
        if (changedListeners == null) {
            changedListeners = new HashSet<>();
            instance.changedListebers.put(tClass, changedListeners);
        }
        changedListeners.add(listener);
    }

    public static  <Model extends BaseModel> void removeChangeListener(final Class<Model> tClass, ChangedListener<Model> listener) {
        Set<ChangedListener> mListeners = instance.getListener(tClass);
        if (mListeners == null) {
            return;
        }
        mListeners.remove(listener);
    }


    public interface ChangedListener<Data extends BaseModel> {
        void onDataSave(Data... data);
    }
}

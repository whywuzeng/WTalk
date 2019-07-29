package com.utsoft.jan.lang;

import com.utsoft.jan.lang.bean.DtVersionBean;
import com.utsoft.jan.lang.callback.CmdListener;
import com.utsoft.jan.lang.net.Network;
import com.utsoft.jan.lang.utils.ZIPUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import retrofit2.Response;

/**
 * Created by Administrator on 2019/7/29.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.lang
 */
public class PutPackage {

    public static final String CMD ="C:/Windows/System32/cmd.exe /k ";

    //开发库url
    private static final String DEVURL="dtdebug/dtsversion.json";

    //测试库url
    private static final String TESURL="dtsversion.json";

    //目标文件夹
    private static final String DES_FOLDER="F:\\webwork\\dt2\\dtsweex1\\bundlejsZip";

    private static final String DES_FILE ="dtsversion.json";


    public void setCmdListener(CmdListener cmdListener) {
        this.cmdListener = cmdListener;
    }

    private CmdListener cmdListener;

    public static void main(String arg[]) throws NullPointerException{

//       cd F:\webwork\dt2\dtsweex1
        long start = System.currentTimeMillis();

        String command1 = CMD+"f:";
        String command2 = CMD+"cd F:\\webwork\\dt2\\dtsweex1";
        String command3 = CMD+"weex compile -m src ./bundlejs";

        String path ="F:\\webwork\\dt2\\dtsweex1\\dos_cmd.bat";

        String osName = System.getProperty("os.name");
        if (osName.contains("Windows")) {
//            exec(command1+" && "+command2+" && "+command3+" && ");
            exec(path);

        }

        long end = System.currentTimeMillis();
        System.out.println("用时:{} ms"+ (end - start));

    }

    public static boolean exec(String command) {
        Process process;// Process可以控制该子进程的执行或获取该子进程的信息
        try {
            System.out.println("exec cmd : {}"+command);
            process = Runtime.getRuntime().exec(command);// exec()方法指示Java虚拟机创建一个子进程执行指定的可执行程序，并返回与该子进程对应的Process对象实例。

            // 下面两个可以获取输入输出流
            InputStream errorStream = process.getErrorStream();
            final InputStream inputStream = process.getInputStream();
            BufferedReader  br2 =null;
            try{

            new Thread(new Runnable() {
                public void run() {
                    BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
                    String line = null;
                    try {
                        while ((line=br.readLine()) != null)
                            System.out.println(new String(line.getBytes("8859_1"),"GB2312"));
                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                    finally
                    {
                        try {
                            br.close();
                            inputStream.close();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
            }).start();

              br2 = new BufferedReader(new InputStreamReader(errorStream));
            String line = null;
            while ((line = br2.readLine()) != null)
                System.out.println(new String(line.getBytes("8859_1"),"GB2312"));

        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }
        finally
        {
            try {
                br2.close();
                errorStream.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

        } catch (IOException e) {
            System.out.println(" exec {} error"+ command+ e);
            return false;
        }

        int exitStatus = 0;
        try {
            exitStatus = process.waitFor();// 等待子进程完成再往下执行，返回值是子线程执行完毕的返回值
            // 第二种接受返回值的方法
            int i = process.exitValue(); // 接收执行完毕的返回值

            System.out.println("i----" + i);

            //回调命令执行成功的事件.
            Response<DtVersionBean> rsp = Network.remote().getDtsVersion(DEVURL)
                    .execute();
            if (rsp.code() == 200)
            {
                System.out.println("dtsVersion 获取成功..");
                DtVersionBean versionBean = rsp.body();
                String bundleVersion = versionBean.getBundleVersion();
                String countNum = AddBundleVersion(bundleVersion);

                versionBean.setBundleVersion(countNum);

                String srcJson = Network.getGson().toJson(versionBean);

                File srcFiles = new File(DES_FOLDER);

                File srcFile = new File(DES_FOLDER, DES_FILE);

                if (!srcFiles.exists())
                {
                    if (srcFiles.mkdirs())
                    {
                        saveFile(srcJson,srcFile.getAbsolutePath());
                    }
                }else {
                        saveFile(srcJson,srcFile.getAbsolutePath());
                }

                //F:\webwork\dt2\dtsweex1\bundlejs 原文件
                //F:\webwork\dt2\dtsweex1\bundlejsZip\dtsbundlejs.zip  目标文件
//                compress("H:\\zip/scala","H:\\zip/oo.zip");
                ZIPUtil.compress("F:\\webwork\\dt2\\dtsweex1\\bundlejs","F:\\webwork\\dt2\\dtsweex1\\bundlejsZip\\dtsbundlejs.zip");

                //上传文件即可

            }else {

                System.out.println("dtsVersion 获取成功..");
                return false;
            }
        } catch (InterruptedException e) {
            System.out.println(" InterruptedException  exec {}"+ command+ e);
            return false;
        } catch (IOException e) {
            e.printStackTrace();

        }

        if (exitStatus != 0) {
            System.out.println("exec cmd exitStatus {}"+ exitStatus);
        } else {
            System.out.println("exec cmd exitStatus {}"+ exitStatus);
        }

        process.destroy(); // 销毁子进程
        process = null;

        return true;
    }

    private static String AddBundleVersion(String bundleVersion) {
        String repStr = bundleVersion.replaceAll("\\.", "");
        System.out.println("转换后的版本"+repStr);
        Integer countSum = Integer.valueOf(repStr);
        countSum++;
//        6.11.26
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(countSum));
        stringBuilder.insert(3,".");
        stringBuilder.insert(1,".");
        return stringBuilder.toString();
    }

    /**
     * string 保存到文件
     * @param content
     */
    private static void saveFile(String content,String path) {

        FileWriter fwriter = null;
        try {
            fwriter = new FileWriter(path);
            fwriter.write(content);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                fwriter.flush();
                fwriter.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

}

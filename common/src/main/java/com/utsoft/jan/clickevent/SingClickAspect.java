//package com.utsoft.jan.clickevent;
//
//import android.util.Log;
//
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.aspectj.lang.reflect.MethodSignature;
//
//import java.lang.reflect.Method;
//
///**
// * Created by Administrator on 2019/8/19.
// * <p>
// * by author wz
// * <p>
// * com.utsoft.jan.clickevent
// */
//@Aspect
//public class SingClickAspect {
//    private static final String TAG = "SingClickAspect";
//
//    public static final long DEFAULT_TIME_INTERVAL = 5000L;
//
//    @Pointcut("execution(@com.utsoft.jan.clickevent.SingleClick * *(..))")
//    public void methodAnnotated() {
//    }
//
//    /**
//     * 切面方法
//     */
//
//    @Around("methodAnnotated()")
//    public void aroundJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
//        Log.e(TAG, "aroundJoinPoint:" );
//        //int  viewId = -1;
//        //
//        ////取出方法的参数
//        //for (Object arg : joinPoint.getArgs()) {
//        //    if (arg instanceof Integer) {
//        //        viewId = (Integer) arg;
//        //        break;
//        //    }
//        //}
//        //
//        //if (viewId == -1)
//        //    return;
//
//        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
//
//        Method method = signature.getMethod();
//
//        if (!method.isAnnotationPresent(SingleClick.class))
//            return;
//
//        SingleClick singleClick = method.getAnnotation(SingleClick.class);
//
//        if (!XClickUtil.isFastDoubleClick(singleClick.valueId()[0],singleClick.value())){
//            joinPoint.proceed();
//        }
//
//    }
//}

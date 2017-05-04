package cn.tianqu.libs.app.common.permission2;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 权限工具类
 * Created by Manfi on 2017/5/3.
 */

public class PermissionUtils {

    private static final String TAG = PermissionUtils.class.getSimpleName();
    private static final String DIALOG_TAG = "RationaleDialogFragmentCompat";

    public interface PermissionCallbacks extends ActivityCompat.OnRequestPermissionsResultCallback {

        void onPermissionGranted(int requestCode, List<String> perms);

        void onPermissionDenied(int requestCode, List<String> perms);
    }

    /**
     * 判断权限是否已经授权
     *
     * @param context ~
     * @param perms   {@link android.Manifest.permission}
     * @return ~
     */
    public static boolean hasPermission(Context context, String... perms) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }

        for (String perm : perms) {
            boolean hasPerm = (ContextCompat.checkSelfPermission(context, perm) == PackageManager.PERMISSION_GRANTED);
            if (!hasPerm) {
                return false;
            }
        }

        return true;
    }

    public static void requestPermissions(final Object object, String rationale, final int requestCode, final String... perms) {
        checkCallingObjectSuitability(object);

        boolean shouldShowRationale = false;
        for (String perm : perms) {
            shouldShowRationale = shouldShowRequestPermissionRationale(object, perm);
        }

        if (shouldShowRationale) {
            // 提示申请权限缘由
            // TODO: 2017/5/4
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    static void executePermissionsRequest(Object object, String[] perms, int requestCode) {
        checkCallingObjectSuitability(object);

        if (object instanceof Activity) {
            ActivityCompat.requestPermissions((Activity) object, perms, requestCode);
        } else if (object instanceof android.support.v4.app.Fragment) {
            ((android.support.v4.app.Fragment) object).requestPermissions(perms, requestCode);
        } else if (object instanceof Fragment) {
            ((Fragment) object).requestPermissions(perms, requestCode);
        }
    }

    public static void onRequestPermissionsResult(int requestCode, String[] perms, int[] grantResults, Object... receivers) {
        ArrayList<String> granted = new ArrayList<>();
        ArrayList<String> denied = new ArrayList<>();
        for (int i = 0; i < perms.length; i++) {
            String perm = perms[i];
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                granted.add(perm);
            } else {
                denied.add(perm);
            }
        }

        for (Object receiver : receivers) {
            if (!granted.isEmpty()) {
                if (receiver instanceof PermissionCallbacks) {
                    ((PermissionCallbacks) receiver).onPermissionGranted(requestCode, granted);
                }
            }

            if (!denied.isEmpty()) {
                if (receiver instanceof PermissionCallbacks) {
                    ((PermissionCallbacks) receiver).onPermissionDenied(requestCode, denied);
                }
            }

            // 所有权限都已经授予
            if (!granted.isEmpty() && denied.isEmpty())
                runAfterPermissionGrantedMethod(receiver, requestCode);
        }
    }

    /**
     * 解析AfterPermissionGranted注解方法
     *
     * @param object      ~
     * @param requestCode ~
     */
    private static void runAfterPermissionGrantedMethod(Object object, int requestCode) {
        Class clazz = object.getClass();

        for (Method method : clazz.getMethods()) {
            if (method.isAnnotationPresent(AfterPermissionGranted.class)) {
                AfterPermissionGranted ann = method.getAnnotation(AfterPermissionGranted.class);
                if (ann.value() == requestCode) {
                    if (method.getParameterTypes().length > 0) {
                        throw new RuntimeException(
                                "Cannot execute method" + method.getName() + " because it is non-void method and/or has input parameters."
                        );
                    }
                }

                try {
                    if (!method.isAccessible()) {
                        method.setAccessible(true);
                    }
                    method.invoke(object);
                } catch (InvocationTargetException e) {
                    Log.e(TAG, "runDefaultMethod: IllegalAccessException", e);
                } catch (IllegalAccessException e) {
                    Log.e(TAG, "runDefaultMethod: InvocationTargetException", e);
                }
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private static boolean shouldShowRequestPermissionRationale(Object object, String perm) {
        if (object instanceof Activity) {
            return ActivityCompat.shouldShowRequestPermissionRationale((Activity) object, perm);
        } else if (object instanceof android.support.v4.app.Fragment) {
            return ((android.support.v4.app.Fragment) object).shouldShowRequestPermissionRationale(perm);
        } else if (object instanceof Fragment) {
            return ((Fragment) object).shouldShowRequestPermissionRationale(perm);
        } else {
            return false;
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private static Activity getActivity(Object object) {
        if (object instanceof Activity) {
            return (Activity) object;
        } else if (object instanceof android.support.v4.app.Fragment) {
            return ((android.support.v4.app.Fragment) object).getActivity();
        } else if (object instanceof Fragment) {
            return ((Fragment) object).getActivity();
        } else {
            return null;
        }
    }

    private static android.support.v4.app.FragmentManager getSupportFragmentManager(Object object) {
        if (object instanceof FragmentActivity) {
            return ((FragmentActivity) object).getSupportFragmentManager();
        } else if (object instanceof android.support.v4.app.Fragment) {
            return ((android.support.v4.app.Fragment) object).getChildFragmentManager();
        }
        return null;
    }

    private static FragmentManager getFragmentManager(Object object) {
        if (object instanceof Activity) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                return ((Activity) object).getFragmentManager();
            }
        } else if (object instanceof Fragment) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                return ((Fragment) object).getChildFragmentManager();
            } else {
                return ((Fragment) object).getFragmentManager();
            }
        }
        return null;
    }

    public static boolean somePermissionsPermanentlyDenied(@NonNull Object object, @NonNull List<String> deniedPermissions) {
        for (String deniedPermission : deniedPermissions) {
            if (permissionPermanentlyDenied(object, deniedPermission)) {
                return true;
            }
        }
        return false;
    }

    public static boolean permissionPermanentlyDenied(@NonNull Object object, @NonNull String deniedPermission) {
        return !shouldShowRequestPermissionRationale(object, deniedPermission);
    }

    private static void checkCallingObjectSuitability(Object object) {
        if (object == null) {
            throw new NullPointerException("Activity or Fragment should not be null");
        }

        boolean isActivity = object instanceof ActivityCompat;
        boolean isSupportFragment = object instanceof Fragment;
        boolean isAppFragment = object instanceof android.app.Fragment;
        boolean isMinSdkM = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;

        if (!(isSupportFragment || isActivity || (isAppFragment && isMinSdkM))) {
            if (isAppFragment) {
                throw new IllegalArgumentException("Target SDK needs to be greater than 23 if caller is android.app.Fragment");
            } else {
                throw new IllegalArgumentException("Caller must be an Activity or a Fragment");
            }
        }
    }
}

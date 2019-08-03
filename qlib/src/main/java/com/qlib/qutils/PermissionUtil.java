package com.qlib.qutils;

public class PermissionUtil {
    private static final String[] perStr = {
            "android.permission.WRITE_CONTACTS",
            "android.permission.GET_ACCOUNTS",
            "android.permission.READ_CONTACTS",
            "android.permission.READ_CALL_LOG",
            "android.permission.READ_PHONE_STATE",
            "android.permission.CALL_PHONE",
            "android.permission.WRITE_CALL_LOG",
            "android.permission.USE_SIP",
            "android.permission.PROCESS_OUTGOING_CALLS",
            "android.permission.READ_CALENDAR",
            "android.permission.WRITE_CALENDAR",
            "android.permission.CAMERA",
            "android.permission.BODY_SENSORS",
            "android.permission.ACCESS_FINE_LOCATION",
            "android.permission.ACCESS_COARSE_LOCATION",
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE",
            "android.permission.RECORD_AUDIO",
            "android.permission.READ_SMS",
            "android.permission.RECEIVE_WAP_PUSH",
            "android.permission.RECEIVE_MMS",
            "android.permission.RECEIVE_SMS",
            "android.permission.SEND_SMS",
            "android.permission.READ_CELL_BROADCASTS"
    };

    private static final String[] perName = {
            "写入但不读取用户联系人数据",
            "访问GMail账户列表",
            "访问联系人通讯录信息",
            "读取通话记录",
            "访问电话状态",
            "拨打电话",
            "写入但不读取用户的联系人数据",
            "使用SIP视频服务 ",
            "监视、修改有关播出电话",
            "读取用户日历数据",
            "写入但不读取用户日历数据",
            "访问摄像头进行拍照",
            "使用定位权限组",
            "访问精准位置",
            "访问粗略位置",
            "读取存储设备",
            "写入存储设备",
            "录制音频",
            "读取短信息",
            "接收WAP PUSH信息",
            "接收彩信",
            "接收短信",
            "发送短信",
            "关闭短信"
    };

    // 获取访问权限名称
    public static String getTip(String permission) {
        String msg = "相关权限";

        int index = getIndex(permission);
        if (index != -1) {
            msg = perName[index];
        }
        return "允许程序" + msg + "权限";
    }

    private static int getIndex(String permission) {
        for (int i = 0; i < perStr.length; i++) {
            if (permission.equals(perStr[i])) {
                return i;
            }
        }

        return -1;
    }
}

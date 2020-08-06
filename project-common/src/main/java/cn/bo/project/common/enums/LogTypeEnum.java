package cn.bo.project.common.enums;

/**
 * @Author zhangbo
 * @Date 2020/8/6 13:12
 * @Description
 * @PackageName cn.bo.project.common.enums
 **/
public enum LogTypeEnum {

    /**
     * 1 登录 2系统操作
     */
    LOGIN(0,"登录"),


    OPERATE(1,"系统操作");

    LogTypeEnum(int value, String label) {
        this.value = value;
        this.label = label;
    }

    private int value;

    private String label;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * 根据value获取label
     * @param value
     * @return
     */
    public static String getLabel(int value) {
        for (LogTypeEnum logTypeEnum : LogTypeEnum.values()) {
            if (logTypeEnum.getValue() == value) {
                return logTypeEnum.getLabel();
            }
        }
        return "";
    }
}

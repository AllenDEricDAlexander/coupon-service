package top.atluofu.enums;


public enum AddressStatusEnum {

    /**
     * @author 有罗敷的马同学
     * @description 默认为0 ， 其他为1
     * @Date 16:18 2023-08-08
     * @Param
     * @Return
     **/
    DEFAULT_STATUS(0), COMMON_STATUS_ENUM(1);
    private final int addressStatus;

    AddressStatusEnum(int status) {
        this.addressStatus = status;
    }

    public int getStatus() {
        return addressStatus;
    }
}

package com.course.server.enums;

public enum FileUseEnum {

    COURSE("C", "课程"),
    TEACHER("T", "讲师");

    private String code;

    private String desc;

    FileUseEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("FileUseEnum{");
        sb.append("code='").append(code).append('\'');
        sb.append(", desc='").append(desc).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public static FileUseEnum getByCode(String code){
        for(FileUseEnum e:FileUseEnum.values()){
            if(code.equals(e.getCode())){
                return e;
            }
        }
        return null;
    }
}

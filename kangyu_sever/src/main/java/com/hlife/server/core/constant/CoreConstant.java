package com.hlife.server.core.constant;

import lombok.Getter;

public class CoreConstant {
    private CoreConstant() {
    }

    /**
     * 性别枚举
     */
    @Getter
    public enum Gender {

        MALE("1", "男"), FEMALE("0", "女");

        private String value;
        private String sex;

        Gender(String value, String sex) {
            this.value = value;
            this.sex = sex;
        }

        public static Gender getInstanceByValue(String value) {
            for (Gender gender : Gender.values()) {
                if (gender.value == value) {
                    return gender;
                }
            }
            throw new RuntimeException("传入性别值有误");
        }

        public static Gender getInstanceBySex(String sex) {
            for (Gender gender : Gender.values()) {
                if (gender.sex.equals(sex)) {
                    return gender;
                }
            }
            throw new RuntimeException("传入性别值有误");
        }

        public static boolean isMale(String sex) {
            return MALE.sex.equals(sex);
        }
    }
}

package com.paranoia.mongo.common;

import lombok.Getter;

/**
 * @author PARANOIA_ZK
 * @date 2018/10/13 22:53
 */
public interface Constant {

    interface Common{
        @Getter
        enum gender {
            MAN("男"),
            WOMAN("女"),
            UNKNOWN("不明");

            private String name;

            gender(String name) {
                this.name = name;
            }
        }
    }

    interface Organization {

        @Getter
        enum organizationType {
            HOSPITAL("医院"),
            COMPANY("企业");

            private String name;

            organizationType(String name) {
                this.name = name;
            }
        }
    }

    interface Platform{

        @Getter
        enum platformType{
            SYSTEM("系统"),
            TERMINAL("终端");
            private String name;

            platformType(String name) {
                this.name = name;
            }
        }
    }

}

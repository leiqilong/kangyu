package com.hlife.server.program.constant;

import lombok.Getter;

public class ProgramConstant {

    public enum FileType {
        IMG_JPG(".jpg", "image/jpeg"),
        IMG_PNG(".png", "image/png"),
        TEXT(".txt", "text/plain"),
        DOC(".doc", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");

        /**
         * 后缀
         */
        @Getter
        private String suffix;

        /**
         * 传输文件类型
         */
        @Getter
        private String contentType;

        FileType(String suffix, String contentType) {
            this.suffix = suffix;
            this.contentType = contentType;
        }

        public static FileType getInstance(String contentType) {
            for (FileType type: FileType.values()) {
                if (type.contentType.equals(contentType)) {
                    return type;
                }
            }
            throw new RuntimeException("没有对应的文件类型");
        }
    }
}

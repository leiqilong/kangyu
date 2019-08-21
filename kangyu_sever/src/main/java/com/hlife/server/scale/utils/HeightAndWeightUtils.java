package com.hlife.server.scale.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hlife.server.core.constant.CoreConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 量表常量类
 */

public class HeightAndWeightUtils {
    private HeightAndWeightUtils() {
    }

    public final static int[] MONTH_ARRAY_ALL = new int[217];

    static {
        for (int i = 0, length = MONTH_ARRAY_ALL.length; i < length; i++) {
            MONTH_ARRAY_ALL[i] = i;
        }
    }

    public final static int[] MONTH_ARRAY = {0, 2, 4, 6, 9, 12, 15, 18, 21, 24, 30, 36, 42, 48, 54, 60, 66, 72, 78, 84, 90, 96, 102, 108, 114, 120, 126, 132, 138, 144, 150, 156, 162, 168, 174, 180, 186, 192, 198, 204, 216};

    public final static double[] HEIGHT_MAIL_P3_ARRAY = {47.1, 54.6, 60.3, 64, 67.9, 72.5, 74.4, 76.9, 79.5, 82.1, 86.4, 89.7, 93.4, 96.7, 100, 103.3, 106.4, 109.1, 111.7, 114.6, 117.4, 119.9, 122.3, 124.6, 126.7, 128.7, 130.7, 132.7, 135.3, 138.1, 141.1, 145, 148.8, 152.3, 155.3, 157.5, 159.1, 159.9, 160.5, 160.9, 161.3};

    public final static double[] HEIGHT_MAIL_P10_ARRAY = {48.1, 55.9, 61.7, 65.4, 69.4, 73.1, 76.1, 78.7, 81.4, 84.1, 88.6, 91.9, 95.7, 99.1, 102.4, 105.8, 109, 111.8, 114.5, 117.6, 120.5, 123.1, 125.6, 128, 130.3, 132.3, 134.5, 136.8, 139.5, 142.5, 145.7, 149.6, 153.3, 156.7, 159.4, 161.4, 162.9, 163.6, 164.2, 164.5, 164.9};

    public final static double[] HEIGHT_MAIL_P25_ARRAY = {49.2, 57.2, 63, 66.8, 70.9, 74.7, 77.8, 80.6, 83.4, 86.2, 90.8, 94.2, 98, 101.4, 104.9, 108.4, 111.7, 114.6, 117.4, 120.6, 123.6, 126.3, 129, 131.4, 133.9, 136, 138.3, 140.8, 143.7, 147, 150.4, 154.3, 157.9, 161, 163.6, 165.4, 166.7, 167.4, 167.9, 168.2, 168.6};

    public final static double[] HEIGHT_MAIL_P50_ARRAY = {50.4, 58.7, 64.6, 68.4, 72.6, 76.5, 79.8, 82.7, 85.6, 88.5, 93.3, 96.8, 100.6, 104.1, 107.7, 111.3, 114.7, 117.7, 120.7, 124, 127.1, 130, 132.7, 135.4, 137.9, 140.2, 142.6, 145.3, 148.4, 151.9, 155.6, 159.5, 163, 165.9, 168.2, 169.8, 171, 171.6, 172.1, 172.3, 172.7};

    public final static double[] HEIGHT_MAIL_P75_ARRAY = {51.6, 60.3, 66.2, 70, 74.4, 78.4, 81.8, 84.8, 87.9, 90.9, 95.9, 99.4, 103.2, 106.9, 110.5, 114.2, 117.7, 120.9, 123.9, 127.4, 130.7, 133.7, 136.6, 139.3, 142, 144.4, 147, 149.9, 153.1, 157, 160.8, 164.8, 168.1, 170.7, 172.8, 174.2, 175.2, 175.8, 176.2, 176.4, 176.7};

    public final static double[] HEIGHT_MAIL_P90_ARRAY = {52.7, 61.7, 67.6, 71.5, 75.9, 80.1, 83.6, 86.7, 90, 93.1, 98.2, 101.8, 105.7, 109.3, 113.1, 116.9, 120.5, 123.7, 126.9, 130.5, 133.9, 137.1, 140.1, 142.9, 145.7, 148.2, 150.9, 154, 157.4, 161.5, 165.5, 169.5, 172.7, 175.1, 176.9, 178.2, 179.1, 179.5, 179.9, 180.1, 180.4};

    public final static double[] HEIGHT_MAIL_P97_ARRAY = {53.8, 63, 69, 73, 77.5, 81.8, 85.4, 88.7, 92, 95.3, 100.5, 104.1, 108.1, 111.8, 115.7, 119.6, 123.3, 126.6, 129.9, 133.7, 137.2, 140.4, 143.6, 146.5, 149.4, 152, 154.9, 158.1, 161.7, 166, 170.2, 174.2, 177.2, 179.4, 181, 182, 182.8, 183.2, 183.5, 183.7, 183.9};

    public final static double[] WEIGHT_MAIL_P3_ARRAY = {2.62, 4.53, 5.99, 6.8, 7.56, 8.16, 8.68, 9.19, 9.71, 10.22, 11.11, 11.94, 12.73, 13.52, 14.37, 15.26, 16.09, 16.8, 17.53, 18.48, 19.43, 20.32, 21.18, 22.04, 22.95, 23.89, 24.96, 26.21, 27.59, 29.09, 30.74, 32.82, 35.03, 37.36, 39.53, 41.43, 43.05, 44.28, 45.3, 46.04, 47.01};

    public final static double[] WEIGHT_MAIL_P10_ARRAY = {2.83, 4.88, 6.43, 7.28, 8.09, 8.72, 9.27, 9.81, 10.37, 10.9, 11.85, 12.74, 13.58, 14.43, 15.35, 16.33, 17.26, 18.06, 18.92, 20.04, 21.17, 22.24, 23.28, 24.31, 25.42, 26.55, 27.83, 29.33, 30.97, 32.77, 34.71, 37.04, 39.42, 41.8, 43.94, 45.77, 47.31, 48.47, 49.42, 50.11, 51.02};

    public final static double[] WEIGHT_MAIL_P25_ARRAY = {3.06, 5.25, 6.9, 7.8, 8.66, 9.33, 9.91, 10.48, 11.08, 11.65, 12.66, 13.61, 14.51, 15.43, 16.43, 17.52, 18.56, 19.49, 20.49, 21.81, 23.16, 24.46, 25.73, 26.98, 28.31, 29.66, 31.2, 32.97, 34.91, 37.03, 39.29, 41.9, 44.45, 46.9, 49, 50.75, 52.19, 53.26, 54.13, 54.77, 55.6};

    public final static double[] WEIGHT_MAIL_P50_ARRAY = {3.32, 5.68, 7.45, 8.41, 9.33, 10.05, 10.68, 11.29, 11.93, 12.54, 13.64, 14.65, 15.63, 16.64, 17.75, 18.98, 20.18, 21.26, 22.45, 24.06, 25.72, 27.33, 28.91, 30.46, 32.09, 33.74, 35.58, 37.69, 39.98, 42.49, 45.13, 48.08, 50.85, 53.37, 55.43, 57.08, 58.39, 59.35, 60.12, 60.68, 61.4};

    public final static double[] WEIGHT_MAIL_P75_ARRAY = {3.59, 6.15, 8.04, 9.07, 10.06, 10.83, 11.51, 12.16, 12.86, 13.51, 14.7, 15.8, 16.86, 17.98, 19.22, 20.61, 21.98, 23.26, 24.7, 26.66, 28.7, 30.71, 32.69, 34.61, 36.61, 38.61, 40.81, 43.27, 45.94, 48.86, 51.89, 55.21, 58.21, 60.83, 62.86, 64.4, 65.57, 66.4, 67.05, 67.51, 68.11};

    public final static double[] WEIGHT_MAIL_P90_ARRAY = {3.85, 6.59, 8.61, 9.7, 10.75, 11.58, 12.3, 13.01, 13.75, 14.46, 15.73, 16.92, 18.08, 19.29, 20.67, 22.23, 23.81, 25.29, 27, 29.35, 31.84, 34.31, 36.74, 39.08, 41.49, 43.85, 46.4, 49.2, 52.21, 55.5, 58.9, 62.57, 65.8, 68.53, 70.55, 72, 73.03, 73.73, 74.25, 74.62, 75.08};

    public final static double[] WEIGHT_MAIL_P97_ARRAY = {4.12, 7.05, 9.2, 10.37, 11.49, 12.37, 13.15, 13.9, 14.7, 15.46, 16.83, 18.12, 19.38, 20.71, 22.24, 24, 25.81, 27.55, 29.57, 32.41, 35.45, 38.49, 41.49, 44.35, 47.24, 50.01, 52.93, 56.07, 59.4, 63.04, 66.81, 70.83, 74.33, 77.2, 79.24, 80.6, 81.49, 82.05, 82.44, 82.7, 83};

    public final static double[] HEIGHT_FEMAIL_P3_ARRAY = {46.6, 53.4, 59.1, 62.5, 66.4, 70, 73.2, 76, 78.5, 80.9, 85.2, 88.6, 92.4, 95.8, 99.2, 102.3, 105.4, 108.1, 110.6, 113.3, 116, 118.5, 121, 123.3, 125.7, 128.3, 131.1, 134.2, 137.2, 140.2, 142.9, 145, 146.7, 147.9, 148.9, 149.5, 149.9, 149.9, 149.9, 150.1, 150.4};

    public final static double[] HEIGHT_FEMAIL_P10_ARRAY = {47.5, 54.7, 60.3, 63.9, 67.8, 71.6, 74.9, 77.7, 80.4, 82.9, 87.4, 90.8, 94.6, 98.1, 101.5, 104.8, 108, 110.8, 113.4, 116.2, 119, 121.6, 124.2, 126.7, 129.3, 132.1, 135, 138.2, 141.2, 144.1, 146.6, 148.6, 150.2, 151.3, 152.2, 152.8, 153.1, 153.1, 153.2, 153.4, 153.7};

    public final static double[] HEIGHT_FEMAIL_P25_ARRAY = {48.6, 56, 61.7, 65.2, 69.3, 73.2, 76.6, 79.5, 82.3, 84.9, 89.6, 93.1, 96.8, 100.4, 104, 107.3, 110.6, 113.5, 116.2, 119.2, 122.1, 124.9, 127.6, 130.2, 132.9, 135.9, 138.9, 142.2, 145.2, 148, 150.4, 152.2, 153.7, 154.8, 155.6, 156.1, 156.5, 156.5, 156.5, 156.7, 157};

    public final static double[] HEIGHT_FEMAIL_P50_ARRAY = {49.7, 57.4, 63.1, 66.8, 71, 75, 78.5, 81.5, 84.4, 87.2, 92.1, 95.6, 99.6, 103.0, 106.7, 109.2, 113.5, 116.6, 119.4, 122.5, 125.6, 128.5, 131.3, 134.1, 137, 140.1, 143.3, 146.6, 149.7, 152.4, 154.6, 156.3, 157.6, 158.6, 159.4, 159.8, 160.1, 160.1, 160.2, 160.3, 160.6};

    public final static double[] HEIGHT_FEMAIL_P75_ARRAY = {50.9, 58.9, 64.6, 68.4, 72.8, 76.8, 80.4, 83.6, 86.6, 89.6, 94.6, 98.2, 102, 105.7, 109.5, 113.1, 116.5, 119.7, 122.7, 125.9, 129.1, 132.1, 135.1, 138, 141.1, 144.4, 147.7, 151.1, 154.1, 156.7, 158.8, 160.3, 161.6, 162.4, 163.1, 163.5, 163.8, 163.8, 163.8, 164, 164.2};

    public final static double[] HEIGHT_FEMAIL_P90_ARRAY = {51.9, 60.2, 66, 69.8, 74.3, 78.5, 82.2, 85.5, 88.6, 91.7, 97, 100.5, 104.4, 108.2, 112.1, 115.7, 119.3, 122.5, 125.6, 129, 132.3, 135.4, 138.5, 141.6, 144.8, 148.2, 151.6, 155.2, 158.2, 160.7, 162.6, 164, 165.1, 165.9, 166.5, 166.8, 167.1, 167.1, 167.1, 167.3, 167.5};

    public final static double[] HEIGHT_FEMAIL_P97_ARRAY = {53, 61.6, 67.4, 71.2, 75.9, 80.2, 84, 87.4, 90.7, 94.9, 99.3, 102.9, 106.8, 110.6, 114.7, 118.4, 122, 125.4, 128.6, 132.1, 135.5, 138.7, 141.9, 145.1, 148.5, 152, 155.6, 159.2, 162.1, 164.5, 166.3, 167.6, 168.6, 169.3, 169.8, 170.1, 170.3, 170.3, 170.4, 170.5, 170.7};

    public final static double[] WEIGHT_FEMAIL_P3_ARRAY = {2.57, 4.21, 5.55, 6.34, 7.11, 7.7, 8.22, 8.73, 9.26, 9.76, 10.65, 11.5, 12.32, 13.1, 13.89, 14.64, 15.39, 16.1, 16.8, 17.58, 18.39, 19.2, 20.25, 20.93, 21.89, 22.98, 24.22, 25.74, 27.43, 29.33, 31.22, 33.09, 34.82, 36.38, 37.71, 38.73, 39.51, 39.96, 40.29, 40.44, 40.71};

    public final static double[] WEIGHT_FEMAIL_P10_ARRAY = {2.76, 4.5, 5.93, 6.76, 7.58, 8.2, 8.75, 9.29, 9.86, 10.39, 11.35, 12.27, 13.14, 13.99, 14.85, 15.68, 16.52, 17.32, 18.12, 19.01, 19.95, 20.89, 21.88, 22.93, 24.08, 25.36, 26.8, 28.53, 30.29, 32.42, 34.39, 36.29, 38.01, 39.55, 40.84, 41.83, 42.58, 43.01, 43.32, 43.47, 43.73};

    public final static double[] WEIGHT_FEMAIL_P25_ARRAY = {2.96, 4.82, 6.34, 7.21, 8.08, 8.74, 9.33, 9.91, 10.51, 11.08, 12.12, 13.11, 14.05, 14.97, 15.92, 16.84, 17.78, 18.68, 19.6, 20.62, 21.71, 22.81, 23.99, 25.23, 26.61, 28.15, 29.84, 31.81, 33.86, 36.04, 38.09, 40, 41.69, 43.19, 44.43, 45.36, 46.06, 46.47, 46.76, 46.9, 47.14};

    public final static double[] WEIGHT_FEMAIL_P50_ARRAY = {3.21, 5.21, 6.83, 7.77, 8.69, 9.4, 10.02, 10.65, 11.3, 11.92, 13.05, 14.13, 15.16, 16.17, 17.22, 18.26, 19.66, 20.77, 21.44, 22.64, 23.93, 25.25, 26.67, 28.19, 29.87, 31.76, 33.8, 36.1, 38.4, 40.77, 42.89, 44.79, 46.42, 47.83, 48.91, 49.82, 50.45, 50.81, 51.07, 51.2, 51.41};

    public final static double[] WEIGHT_FEMAIL_P75_ARRAY = {3.49, 5.64, 7.37, 8.37, 9.36, 10.12, 10.79, 11.46, 12.17, 12.84, 14.07, 15.25, 16.38, 17.5, 18.66, 19.83, 21.06, 22.27, 23.51, 24.94, 26.48, 28.05, 29.77, 31.63, 33.72, 36.05, 38.53, 41.24, 43.85, 46.42, 48.6, 50.45, 51.97, 53.23, 54.23, 54.96, 55.49, 55.79, 56.01, 56.11, 56.28};

    public final static double[] WEIGHT_FEMAIL_P90_ARRAY = {3.75, 6.06, 7.9, 8.96, 10.01, 10.82, 11.53, 12.55, 13.01, 13.74, 15.08, 16.36, 17.59, 18.81, 20.1, 21.41, 22.81, 24.19, 25.62, 27.28, 29.08, 30.95, 33, 35.26, 37.79, 40.63, 43.61, 46.78, 49.73, 52.49, 54.71, 56.46, 57.81, 58.88, 59.7, 60.28, 60.69, 60.91, 61.07, 61.15, 61.28};

    public final static double[] WEIGHT_FEMAIL_P97_ARRAY = {4.04, 6.51, 8.47, 9.59, 10.71, 11.57, 12.33, 13.01, 13.93, 14.91, 16, 17.55, 18.89, 20.24, 21.67, 23.14, 24.52, 26.3, 27.96, 29.89, 32.01, 34.23, 36.69, 39.41, 42.51, 45.97, 49.59, 53.33, 56.67, 59.64, 61.86, 63.45, 64.55, 65.36, 65.93, 66.3, 66.55, 66.69, 66.78, 66.82, 66.89};


    public static JSONObject getHeightStandJson(String sex, int maxMonth, int minMonth) {
        if (CoreConstant.Gender.isMale(sex)) {
            return getMaleHeightStandJson(maxMonth, minMonth);
        }
        return getFemaleHeightStandJson(maxMonth, minMonth);
    }

    public static JSONObject getWeightStandJson(String sex, int maxMonth, int minMonth) {
        if (CoreConstant.Gender.isMale(sex)) {
            return getMaleWeightStandJson(maxMonth, minMonth);
        }
        return getFemaleWeightStandJson(maxMonth, minMonth);
    }

    @Data
    @Accessors(chain = true)
    public static class HeightStand {
        private HeightStand() {
        }

        private String gender;

        private int month;

        private double p3Height;

        private double p10Height;

        // private double p25Height;

        private double p50Height;

        // private double p75Height;

        private double p90Height;

        private double p97Height;

        public String getJudgeStand(double height) {
            if (height < this.p3Height) {
                return "下异常";
            }

            if (this.p3Height <= height && height < this.p10Height) {
                return "中下";
            }

            if (this.p10Height <= height && height <= this.p90Height) {
                return "中等";
            }

            if (this.p90Height < height && height <= this.p97Height) {
                return "中等";
            }

            return "上异常";
        }

        @Data
        @Accessors(chain = true)
        @AllArgsConstructor
        // @NoArgsConstructor
        public static class Builder {

            private String gender;
            private Integer month;

            public HeightStand getInstance() {
                if (CoreConstant.Gender.isMale(this.gender)) {
                    return getMaleHeightStand().setMonth(month);
                }
                return getFemaleHeightStand().setMonth(month);
            }

            private HeightStand getMaleHeightStand() {
                int[] indexArray = this.getIndexArray();
                if (indexArray.length == 0) {
                    throw new RuntimeException("年龄超范围");
                }

                if (indexArray.length == 1) {
                    int index = indexArray[0];
                    return new HeightStand()
                            .setP3Height(HEIGHT_MAIL_P3_ARRAY[index])
                            .setP10Height(HEIGHT_MAIL_P10_ARRAY[index])
                            //.setP25Height(HEIGHT_MAIL_P25_ARRAY[index])
                            .setP50Height(HEIGHT_MAIL_P50_ARRAY[index])
                            //.setP75Height(HEIGHT_MAIL_P75_ARRAY[index])
                            .setP90Height(HEIGHT_MAIL_P90_ARRAY[index])
                            .setP97Height(HEIGHT_MAIL_P97_ARRAY[index]);

                }
                int index0 = indexArray[0], index1 = indexArray[1];
                int monthDif = MONTH_ARRAY[index1] - MONTH_ARRAY[index0];
                int monthDifAge = month - MONTH_ARRAY[index0];
                double wights = monthDifAge * 1.0 / monthDif;

                return new HeightStand()
                        .setP3Height(getMiddleValue(HEIGHT_MAIL_P3_ARRAY[index0], HEIGHT_MAIL_P3_ARRAY[index1], wights))
                        .setP10Height(getMiddleValue(HEIGHT_MAIL_P10_ARRAY[index0], HEIGHT_MAIL_P10_ARRAY[index1], wights))
                        //.setP25Height(getMiddleValue(HEIGHT_MAIL_P25_ARRAY[index0], HEIGHT_MAIL_P25_ARRAY[index1], wights))
                        .setP50Height(getMiddleValue(HEIGHT_MAIL_P50_ARRAY[index0], HEIGHT_MAIL_P50_ARRAY[index1], wights))
                        //.setP75Height(getMiddleValue(HEIGHT_MAIL_P75_ARRAY[index0], HEIGHT_MAIL_P75_ARRAY[index1], wights))
                        .setP90Height(getMiddleValue(HEIGHT_MAIL_P90_ARRAY[index0], HEIGHT_MAIL_P90_ARRAY[index1], wights))
                        .setP97Height(getMiddleValue(HEIGHT_MAIL_P97_ARRAY[index0], HEIGHT_MAIL_P97_ARRAY[index1], wights));
            }

            private HeightStand getFemaleHeightStand() {
                int[] indexArray = this.getIndexArray();
                if (indexArray.length == 0) {
                    throw new RuntimeException("年龄超范围");
                }

                if (indexArray.length == 1) {
                    int index = indexArray[0];
                    return new HeightStand()
                            .setP3Height(HEIGHT_FEMAIL_P3_ARRAY[index])
                            .setP10Height(HEIGHT_FEMAIL_P10_ARRAY[index])
                            //.setP25Height(HEIGHT_FEMAIL_P25_ARRAY[index])
                            .setP50Height(HEIGHT_FEMAIL_P50_ARRAY[index])
                            //.setP75Height(HEIGHT_FEMAIL_P75_ARRAY[index])
                            .setP90Height(HEIGHT_FEMAIL_P90_ARRAY[index])
                            .setP97Height(HEIGHT_FEMAIL_P97_ARRAY[index]);

                }
                int index0 = indexArray[0], index1 = indexArray[1];
                int monthDif = MONTH_ARRAY[index1] - MONTH_ARRAY[index0];
                int monthDifAge = this.month - MONTH_ARRAY[index0];
                double wights = monthDifAge * 1.0 / monthDif;

                return new HeightStand()
                        .setP3Height(getMiddleValue(HEIGHT_FEMAIL_P3_ARRAY[index0], HEIGHT_FEMAIL_P3_ARRAY[index1], wights))
                        .setP10Height(getMiddleValue(HEIGHT_FEMAIL_P10_ARRAY[index0], HEIGHT_FEMAIL_P10_ARRAY[index1], wights))
                        //.setP25Height(getMiddleValue(HEIGHT_FEMAIL_P25_ARRAY[index0], HEIGHT_FEMAIL_P25_ARRAY[index1], wights))
                        .setP50Height(getMiddleValue(HEIGHT_FEMAIL_P50_ARRAY[index0], HEIGHT_FEMAIL_P50_ARRAY[index1], wights))
                        //.setP75Height(getMiddleValue(HEIGHT_FEMAIL_P75_ARRAY[index0], HEIGHT_FEMAIL_P75_ARRAY[index1], wights))
                        .setP90Height(getMiddleValue(HEIGHT_FEMAIL_P90_ARRAY[index0], HEIGHT_FEMAIL_P90_ARRAY[index1], wights))
                        .setP97Height(getMiddleValue(HEIGHT_FEMAIL_P97_ARRAY[index0], HEIGHT_FEMAIL_P97_ARRAY[index1], wights));
            }

            private int[] getIndexArray() {
                for (int index = 0, lenght = MONTH_ARRAY.length; index < lenght; index++) {
                    if (MONTH_ARRAY[index] == this.month) {
                        return new int[]{index};
                    }

                    if (MONTH_ARRAY[index] > this.month) {
                        return new int[]{index - 1, index};
                    }
                }
                return new int[]{};
            }

            private double getMiddleValue(double val0, double val1, double wights) {
                return val0 + (val1 - val0) * wights;
            }
        }


    }

    @Data
    @Accessors(chain = true)
    public static class WeightStand {
        private WeightStand() {
        }

        private String gender;

        private int month;

        private double p3Weight;

        private double p10Weight;

        // private double p25Weight;

        private double p50Weight;

        //  private double p75Weight;

        private double p90Weight;

        private double p97Weight;

        public String getJudgeStand(double weight) {
            if (weight < this.p3Weight) {
                return "下异常";
            }

            if (this.p3Weight <= weight && weight < this.p10Weight) {
                return "中下";
            }

            if (this.p10Weight <= weight && weight <= this.p90Weight) {
                return "中等";
            }

            if (this.p90Weight < weight && weight <= this.p97Weight) {
                return "中等";
            }

            return "上异常";
        }

        @Data
        @Accessors(chain = true)
        @AllArgsConstructor
        // @NoArgsConstructor
        public static class Builder {

            private String gender;
            private Integer month;

            public WeightStand getInstance() {
                if (CoreConstant.Gender.isMale(this.gender)) {
                    return this.getMaleWeightStand().setMonth(month);
                }

                return this.getFemaleWeightStand().setMonth(this.month);
            }

            private WeightStand getMaleWeightStand() {
                int[] indexArray = this.getIndexArray();
                if (indexArray.length == 0) {
                    throw new RuntimeException("年龄超范围");
                }

                if (indexArray.length == 1) {
                    int index = indexArray[0];
                    return new WeightStand()
                            .setP3Weight(WEIGHT_MAIL_P3_ARRAY[index])
                            .setP10Weight(WEIGHT_MAIL_P10_ARRAY[index])
                            //.setP25Height(HEIGHT_MAIL_P25_ARRAY[index])
                            .setP50Weight(WEIGHT_MAIL_P50_ARRAY[index])
                            //.setP75Height(HEIGHT_MAIL_P75_ARRAY[index])
                            .setP90Weight(WEIGHT_MAIL_P90_ARRAY[index])
                            .setP97Weight(WEIGHT_MAIL_P97_ARRAY[index]);

                }
                int index0 = indexArray[0], index1 = indexArray[1];
                int monthDif = MONTH_ARRAY[index1] - MONTH_ARRAY[index0];
                int monthDifAge = month - MONTH_ARRAY[index0];
                double wights = monthDifAge * 1.0 / monthDif;

                return new WeightStand()
                        .setP3Weight(getMiddleValue(WEIGHT_MAIL_P3_ARRAY[index0], WEIGHT_MAIL_P3_ARRAY[index1], wights))
                        .setP10Weight(getMiddleValue(WEIGHT_MAIL_P10_ARRAY[index0], WEIGHT_MAIL_P10_ARRAY[index1], wights))
                        //.setP25Weight(getMiddleValue(WEIGHT_MAIL_P25_ARRAY[index0], WEIGHT_MAIL_P25_ARRAY[index1], wights))
                        .setP50Weight(getMiddleValue(WEIGHT_MAIL_P50_ARRAY[index0], WEIGHT_MAIL_P50_ARRAY[index1], wights))
                        //.setP75Weight(getMiddleValue(WEIGHT_MAIL_P75_ARRAY[index0], WEIGHT_MAIL_P75_ARRAY[index1], wights))
                        .setP90Weight(getMiddleValue(WEIGHT_MAIL_P90_ARRAY[index0], WEIGHT_MAIL_P90_ARRAY[index1], wights))
                        .setP97Weight(getMiddleValue(WEIGHT_MAIL_P97_ARRAY[index0], WEIGHT_MAIL_P97_ARRAY[index1], wights));

            }

            private WeightStand getFemaleWeightStand() {
                int[] indexArray = this.getIndexArray();
                if (indexArray.length == 0) {
                    throw new RuntimeException("年龄超范围");
                }

                if (indexArray.length == 1) {
                    int index = indexArray[0];
                    return new WeightStand()
                            .setP3Weight(WEIGHT_FEMAIL_P3_ARRAY[index])
                            .setP10Weight(WEIGHT_FEMAIL_P10_ARRAY[index])
                            //.setP25Height(WEIGHT_FEMAIL_P25_ARRAY[index])
                            .setP50Weight(WEIGHT_FEMAIL_P50_ARRAY[index])
                            //.setP75Weight(WEIGHT_FEMAIL_P75_ARRAY[index])
                            .setP90Weight(WEIGHT_FEMAIL_P90_ARRAY[index])
                            .setP97Weight(WEIGHT_FEMAIL_P97_ARRAY[index]);

                }
                int index0 = indexArray[0], index1 = indexArray[1];
                int monthDif = MONTH_ARRAY[index1] - MONTH_ARRAY[index0];
                int monthDifAge = this.month - MONTH_ARRAY[index0];
                double wights = monthDifAge * 1.0 / monthDif;

                return new WeightStand()
                        .setP3Weight(getMiddleValue(WEIGHT_FEMAIL_P3_ARRAY[index0], WEIGHT_FEMAIL_P3_ARRAY[index1], wights))
                        .setP10Weight(getMiddleValue(WEIGHT_FEMAIL_P10_ARRAY[index0], WEIGHT_FEMAIL_P10_ARRAY[index1], wights))
                        //.setP25Weight(getMiddleValue(WEIGHT_FEMAIL_P25_ARRAY[index0], WEIGHT_FEMAIL_P25_ARRAY[index1], wights))
                        .setP50Weight(getMiddleValue(WEIGHT_FEMAIL_P50_ARRAY[index0], WEIGHT_FEMAIL_P50_ARRAY[index1], wights))
                        //.setP75Weight(getMiddleValue(WEIGHT_FEMAIL_P75_ARRAY[index0], WEIGHT_FEMAIL_P75_ARRAY[index1], wights))
                        .setP90Weight(getMiddleValue(WEIGHT_FEMAIL_P90_ARRAY[index0], WEIGHT_FEMAIL_P90_ARRAY[index1], wights))
                        .setP97Weight(getMiddleValue(WEIGHT_FEMAIL_P97_ARRAY[index0], WEIGHT_FEMAIL_P97_ARRAY[index1], wights));
            }


            private int[] getIndexArray() {
                for (int index = 0, lenght = MONTH_ARRAY.length; index < lenght; index++) {
                    if (MONTH_ARRAY[index] == this.month) {
                        return new int[]{index};
                    }

                    if (MONTH_ARRAY[index] > this.month) {
                        return new int[]{index - 1, index};
                    }
                }
                return new int[]{};
            }

            private double getMiddleValue(double val0, double val1, double wights) {
                return val0 + (val1 - val0) * wights;
            }

        }

    }

    private static JSONObject getMaleHeightStandJson(int maxMonth, int minMonth) {
        JSONArray p3Array = new JSONArray(), p10Array = new JSONArray(), p25Array = new JSONArray(),
                p50Array = new JSONArray(), p75Array = new JSONArray(), p90Array = new JSONArray(),
                p97Array = new JSONArray(), monthArray = new JSONArray();

        for (int month = 0; month <= maxMonth; month++) {
            monthArray.add(month);

            int index = getMonthArrayIndex(month);
            if (index == -1) {
                continue;
            }
        //for (int index = 0, lenght = MONTH_ARRAY.length; index < lenght; index++) {

            p3Array.add(new double[]{MONTH_ARRAY[index], HEIGHT_MAIL_P3_ARRAY[index]});
            p10Array.add(new double[]{MONTH_ARRAY[index], HEIGHT_MAIL_P10_ARRAY[index]});
            p25Array.add(new double[]{MONTH_ARRAY[index], HEIGHT_MAIL_P25_ARRAY[index]});
            p50Array.add(new double[]{MONTH_ARRAY[index], HEIGHT_MAIL_P50_ARRAY[index]});
            p75Array.add(new double[]{MONTH_ARRAY[index], HEIGHT_MAIL_P75_ARRAY[index]});
            p90Array.add(new double[]{MONTH_ARRAY[index], HEIGHT_MAIL_P90_ARRAY[index]});
            p97Array.add(new double[]{MONTH_ARRAY[index], HEIGHT_MAIL_P97_ARRAY[index]});
        }
        return new JSONObject()
                //.fluentPut("monthArray", MONTH_ARRAY_ALL)
                .fluentPut("monthArray", monthArray)
                .fluentPut("P3Array", p3Array)
                .fluentPut("P10Array", p10Array)
                .fluentPut("P25Array", p25Array)
                .fluentPut("P50Array", p50Array)
                .fluentPut("P75Array", p75Array)
                .fluentPut("P90Array", p90Array)
                .fluentPut("P97Array", p97Array);
    }


    private static JSONObject getFemaleHeightStandJson(int maxMonth, int minMonth) {
        JSONArray p3Array = new JSONArray(), p10Array = new JSONArray(), p25Array = new JSONArray(),
                p50Array = new JSONArray(), p75Array = new JSONArray(), p90Array = new JSONArray(),
                p97Array = new JSONArray(), monthArray = new JSONArray();

        //for (int index = 0, lenght = MONTH_ARRAY.length; index < lenght; index++) {
        for (int month = 0; month <= maxMonth; month++) {
            monthArray.add(month);

            int index = getMonthArrayIndex(month);
            if (index == -1) {
                continue;
            }

            p3Array.add(new double[]{MONTH_ARRAY[index], HEIGHT_FEMAIL_P3_ARRAY[index]});
            p10Array.add(new double[]{MONTH_ARRAY[index], HEIGHT_FEMAIL_P10_ARRAY[index]});
            p25Array.add(new double[]{MONTH_ARRAY[index], HEIGHT_FEMAIL_P25_ARRAY[index]});
            p50Array.add(new double[]{MONTH_ARRAY[index], HEIGHT_FEMAIL_P50_ARRAY[index]});
            p75Array.add(new double[]{MONTH_ARRAY[index], HEIGHT_FEMAIL_P75_ARRAY[index]});
            p90Array.add(new double[]{MONTH_ARRAY[index], HEIGHT_FEMAIL_P90_ARRAY[index]});
            p97Array.add(new double[]{MONTH_ARRAY[index], HEIGHT_FEMAIL_P97_ARRAY[index]});
        }

        /*return new JSONObject().fluentPut("P3Array", HEIGHT_FEMAIL_P3_ARRAY)
                .fluentPut("P10Array", HEIGHT_FEMAIL_P10_ARRAY)
                .fluentPut("P25Array", HEIGHT_FEMAIL_P25_ARRAY)
                .fluentPut("P50Array", HEIGHT_FEMAIL_P50_ARRAY)
                .fluentPut("P75Array", HEIGHT_FEMAIL_P75_ARRAY)
                .fluentPut("P90Array", HEIGHT_FEMAIL_P90_ARRAY)
                .fluentPut("P97Array", HEIGHT_FEMAIL_P97_ARRAY);*/
        return new JSONObject()
                //.fluentPut("monthArray", MONTH_ARRAY_ALL)
                .fluentPut("monthArray", monthArray)
                .fluentPut("P3Array", p3Array)
                .fluentPut("P10Array", p10Array)
                .fluentPut("P25Array", p25Array)
                .fluentPut("P50Array", p50Array)
                .fluentPut("P75Array", p75Array)
                .fluentPut("P90Array", p90Array)
                .fluentPut("P97Array", p97Array);
    }


    private static JSONObject getMaleWeightStandJson(int maxMonth, int minMonth) {
        JSONArray p3Array = new JSONArray(), p10Array = new JSONArray(), p25Array = new JSONArray(),
                p50Array = new JSONArray(), p75Array = new JSONArray(), p90Array = new JSONArray(),
                p97Array = new JSONArray(), monthArray = new JSONArray();

        //for (int index = 0, lenght = MONTH_ARRAY.length; index < lenght; index++) {
        for (int month = 0; month <= maxMonth; month++) {
            monthArray.add(month);

            int index = getMonthArrayIndex(month);
            if (index == -1) {
                continue;
            }
            p3Array.add(new double[]{MONTH_ARRAY[index], WEIGHT_MAIL_P3_ARRAY[index]});
            p10Array.add(new double[]{MONTH_ARRAY[index], WEIGHT_MAIL_P10_ARRAY[index]});
            p25Array.add(new double[]{MONTH_ARRAY[index], WEIGHT_MAIL_P25_ARRAY[index]});
            p50Array.add(new double[]{MONTH_ARRAY[index], WEIGHT_MAIL_P50_ARRAY[index]});
            p75Array.add(new double[]{MONTH_ARRAY[index], WEIGHT_MAIL_P75_ARRAY[index]});
            p90Array.add(new double[]{MONTH_ARRAY[index], WEIGHT_MAIL_P90_ARRAY[index]});
            p97Array.add(new double[]{MONTH_ARRAY[index], WEIGHT_MAIL_P97_ARRAY[index]});
        }

        return new JSONObject()
                //.fluentPut("monthArray", MONTH_ARRAY_ALL)
                .fluentPut("monthArray", monthArray)
                .fluentPut("P3Array", p3Array)
                .fluentPut("P10Array", p10Array)
                .fluentPut("P25Array", p25Array)
                .fluentPut("P50Array", p50Array)
                .fluentPut("P75Array", p75Array)
                .fluentPut("P90Array", p90Array)
                .fluentPut("P97Array", p97Array);
        /*return new JSONObject().fluentPut("monthArray", MONTH_ARRAY)
                .fluentPut("P3Array", WEIGHT_MAIL_P3_ARRAY)
                .fluentPut("P10Array", WEIGHT_MAIL_P10_ARRAY)
                .fluentPut("P25Array", WEIGHT_MAIL_P25_ARRAY)
                .fluentPut("P50Array", WEIGHT_MAIL_P50_ARRAY)
                .fluentPut("P75Array", WEIGHT_MAIL_P75_ARRAY)
                .fluentPut("P90Array", WEIGHT_MAIL_P90_ARRAY)
                .fluentPut("P97Array", WEIGHT_MAIL_P97_ARRAY);*/
    }

    private static JSONObject getFemaleWeightStandJson(int maxMonth, int minMonth) {
        JSONArray p3Array = new JSONArray(), p10Array = new JSONArray(), p25Array = new JSONArray(),
                p50Array = new JSONArray(), p75Array = new JSONArray(), p90Array = new JSONArray(),
                p97Array = new JSONArray(), monthArray = new JSONArray();

        //for (int index = 0, lenght = MONTH_ARRAY.length; index < lenght; index++) {
        for (int month = 0; month <= maxMonth; month++) {
            monthArray.add(month);

            int index = getMonthArrayIndex(month);
            if (index == -1) {
                continue;
            }
            p3Array.add(new double[]{MONTH_ARRAY[index], WEIGHT_FEMAIL_P3_ARRAY[index]});
            p10Array.add(new double[]{MONTH_ARRAY[index], WEIGHT_FEMAIL_P10_ARRAY[index]});
            p25Array.add(new double[]{MONTH_ARRAY[index], WEIGHT_FEMAIL_P25_ARRAY[index]});
            p50Array.add(new double[]{MONTH_ARRAY[index], WEIGHT_FEMAIL_P50_ARRAY[index]});
            p75Array.add(new double[]{MONTH_ARRAY[index], WEIGHT_FEMAIL_P75_ARRAY[index]});
            p90Array.add(new double[]{MONTH_ARRAY[index], WEIGHT_FEMAIL_P90_ARRAY[index]});
            p97Array.add(new double[]{MONTH_ARRAY[index], WEIGHT_FEMAIL_P97_ARRAY[index]});
        }


        return new JSONObject()
                //.fluentPut("monthArray", MONTH_ARRAY_ALL)
                .fluentPut("monthArray", monthArray)
                .fluentPut("P3Array", p3Array)
                .fluentPut("P10Array", p10Array)
                .fluentPut("P25Array", p25Array)
                .fluentPut("P50Array", p50Array)
                .fluentPut("P75Array", p75Array)
                .fluentPut("P90Array", p90Array)
                .fluentPut("P97Array", p97Array);
        /*return new JSONObject().fluentPut("monthArray", MONTH_ARRAY)
                .fluentPut("P3Array", WEIGHT_FEMAIL_P3_ARRAY)
                .fluentPut("P10Array", WEIGHT_FEMAIL_P10_ARRAY)
                .fluentPut("P25Array", WEIGHT_FEMAIL_P25_ARRAY)
                .fluentPut("P50Array", WEIGHT_FEMAIL_P50_ARRAY)
                .fluentPut("P75Array", WEIGHT_FEMAIL_P75_ARRAY)
                .fluentPut("P90Array", WEIGHT_FEMAIL_P90_ARRAY)
                .fluentPut("P97Array", WEIGHT_FEMAIL_P97_ARRAY);*/
    }


    private static int getMonthArrayIndex(int month) {
        for (int i = 0, lenght = MONTH_ARRAY.length; i < lenght; i++) {
            if (MONTH_ARRAY[i] == month) {
                return i;
            }
        }

        return -1;
    }

}

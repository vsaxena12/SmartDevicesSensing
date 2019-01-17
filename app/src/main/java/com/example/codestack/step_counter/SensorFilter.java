package com.example.codestack.step_counter;

public class SensorFilter {

        private SensorFilter() {
        }

        public static float sum(float[] array) {
            float retval = 0;
            int i=0;
            while (i < array.length) {
                retval += array[i];
                i++;
            }
            return retval;
        }

        public static float[] cross(float[] arrayA, float[] arrayB) {
            float[] retArray = new float[3];
            retArray[0] = arrayA[1] * arrayB[2] - arrayA[2] * arrayB[1];
            retArray[1] = arrayA[2] * arrayB[0] - arrayA[0] * arrayB[2];
            retArray[2] = arrayA[0] * arrayB[1] - arrayA[1] * arrayB[0];
            return retArray;
        }

        public static float norm(float[] array) {
            float retval = 0;
            int i = 0;
            while (i < array.length) {
                retval += array[i] * array[i];
                i++;
            }
            return (float) Math.sqrt(retval);
        }


        public static float dot(float[] a, float[] b) {
            float retval = a[0] * b[0] + a[1] * b[1] + a[2] * b[2];
            return retval;
        }

        public static float[] normalize(float[] a) {
            float[] retval = new float[a.length];
            float norm = norm(a);
            int i = 0;
            while (i < a.length) {
                retval[i] = a[i] / norm;
                i++;
            }
            return retval;
        }


}

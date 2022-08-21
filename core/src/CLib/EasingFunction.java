package CLib;

import model.CRes;

public class EasingFunction {

    private static float NATURAL_LOG_OF_2 = 0.6931472f;

    public static float Linear(float start, float end, float value) {
        return CRes.Lerp(start, end, value);
    }

    public static float Spring(float start, float end, float value) {
        value = CRes.Clamp01(value);
        value = (float) ((Math.sin(value * 3.14f * (0.2f + 2.5f * value * value * value))
                * Math.pow(1.0f - value, 2.200000047683716) + value) * (1.0f + 1.2f * (1.0f - value)));
        return start + (end - start) * value;
    }

    public static float EaseInQuad(float start, float end, float value) {
        end -= start;
        return end * value * value + start;
    }

    public static float EaseOutQuad(float start, float end, float value) {
        end -= start;
        return -end * value * (value - 2.0f) + start;
    }

    public static float EaseInOutQuad(float start, float end, float value) {
        value /= 0.5f;
        end -= start;
        if (value < 1.0f) {
            return end * 0.5f * value * value + start;
        }
        --value;
        return -end * 0.5f * (value * (value - 2.0f) - 1.0f) + start;
    }

    public static float EaseInCubic(float start, float end, float value) {
        end -= start;
        return end * value * value * value + start;
    }

    public static float EaseOutCubic(float start, float end, float value) {
        --value;
        end -= start;
        return end * (value * value * value + 1.0f) + start;
    }

    public static float EaseInOutCubic(float start, float end, float value) {
        value /= 0.5f;
        end -= start;
        if (value < 1.0f) {
            return end * 0.5f * value * value * value + start;
        }
        value -= 2.0f;
        return end * 0.5f * (value * value * value + 2.0f) + start;
    }

    public static float EaseInQuart(float start, float end, float value) {
        end -= start;
        return end * value * value * value * value + start;
    }

    public static float EaseOutQuart(float start, float end, float value) {
        --value;
        end -= start;
        return -end * (value * value * value * value - 1.0f) + start;
    }

    public static float EaseInOutQuart(float start, float end, float value) {
        value /= 0.5f;
        end -= start;
        if (value < 1.0f) {
            return end * 0.5f * value * value * value * value + start;
        }
        value -= 2.0f;
        return -end * 0.5f * (value * value * value * value - 2.0f) + start;
    }

    public static float EaseInQuint(float start, float end, float value) {
        end -= start;
        return end * value * value * value * value * value + start;
    }

    public static float EaseOutQuint(float start, float end, float value) {
        --value;
        end -= start;
        return end * (value * value * value * value * value + 1.0f) + start;
    }

    public static float EaseInOutQuint(float start, float end, float value) {
        value /= 0.5f;
        end -= start;
        if (value < 1.0f) {
            return end * 0.5f * value * value * value * value * value + start;
        }
        value -= 2.0f;
        return end * 0.5f * (value * value * value * value * value + 2.0f) + start;
    }

    public static float EaseInSine(float start, float end, float value) {
        end -= start;
        return (float) (-end * Math.cos(value * 1.5707963267948966) + end + start);
    }

    public static float EaseOutSine(float start, float end, float value) {
        end -= start;
        return (float) (end * Math.sin(value * 1.5707963267948966) + start);
    }

    public static float EaseInOutSine(float start, float end, float value) {
        end -= start;
        return (float) (-end * 0.5f * (Math.cos(3.141592653589793 * value) - 1.0) + start);
    }

    public static float EaseInExpo(float start, float end, float value) {
        end -= start;
        return (float) (end * Math.pow(2.0, 10.0f * (value - 1.0f)) + start);
    }

    public static float EaseOutExpo(float start, float end, float value) {
        end -= start;
        return (float) (end * (-Math.pow(2.0, -10.0f * value) + 1.0) + start);
    }

    public static float EaseInOutExpo(float start, float end, float value) {
        value /= 0.5f;
        end -= start;
        if (value < 1.0f) {
            return (float) (end * 0.5f * Math.pow(2.0, 10.0f * (value - 1.0f)) + start);
        }
        --value;
        return (float) (end * 0.5f * (-Math.pow(2.0, -10.0f * value) + 2.0) + start);
    }

    public static float EaseInCirc(float start, float end, float value) {
        end -= start;
        return (float) (-end * (Math.sqrt(1.0f - value * value) - 1.0) + start);
    }

    public static float EaseOutCirc(float start, float end, float value) {
        --value;
        end -= start;
        return (float) (end * Math.sqrt(1.0f - value * value) + start);
    }

    public static float EaseInOutCirc(float start, float end, float value) {
        value /= 0.5f;
        end -= start;
        if (value < 1.0f) {
            return (float) (-end * 0.5f * (Math.sqrt(1.0f - value * value) - 1.0) + start);
        }
        value -= 2.0f;
        return (float) (end * 0.5f * (Math.sqrt(1.0f - value * value) + 1.0) + start);
    }

    public static float EaseInBounce(float start, float end, float value) {
        end -= start;
        float d = 1.0f;
        return end - EaseOutBounce(0.0f, end, d - value) + start;
    }

    public static float EaseOutBounce(float start, float end, float value) {
        value /= 1.0f;
        end -= start;
        if (value < 0.36363637f) {
            return end * (7.5625f * value * value) + start;
        }
        if (value < 0.72727275f) {
            value -= 0.54545456f;
            return end * (7.5625f * value * value + 0.75f) + start;
        }
        if (value < 0.9090909090909091) {
            value -= 0.8181818f;
            return end * (7.5625f * value * value + 0.9375f) + start;
        }
        value -= 0.95454544f;
        return end * (7.5625f * value * value + 0.984375f) + start;
    }

    public static float EaseInOutBounce(float start, float end, float value) {
        end -= start;
        float d = 1.0f;
        if (value < d * 0.5f) {
            return EaseInBounce(0.0f, end, value * 2.0f) * 0.5f + start;
        }
        return EaseOutBounce(0.0f, end, value * 2.0f - d) * 0.5f + end * 0.5f + start;
    }

    public static float EaseInBack(float start, float end, float value) {
        end -= start;
        value /= 1.0f;
        float s = 1.70158f;
        return end * value * value * ((s + 1.0f) * value - s) + start;
    }

    public static float EaseOutBack(float start, float end, float value) {
        float s = 1.70158f;
        end -= start;
        --value;
        return end * (value * value * ((s + 1.0f) * value + s) + 1.0f) + start;
    }

    public static float EaseInOutBack(float start, float end, float value) {
        float s = 1.70158f;
        end -= start;
        value /= 0.5f;
        if (value < 1.0f) {
            s *= 1.525f;
            return end * 0.5f * (value * value * ((s + 1.0f) * value - s)) + start;
        }
        value -= 2.0f;
        s *= 1.525f;
        return end * 0.5f * (value * value * ((s + 1.0f) * value + s) + 2.0f) + start;
    }

    public static float EaseInElastic(float start, float end, float value) {
        end -= start;
        float d = 1.0f;
        float p = d * 0.3f;
        float a = 0.0f;
        if (value == 0.0f) {
            return start;
        }
        if ((value /= d) == 1.0f) {
            return start + end;
        }
        float s;
        if (a == 0.0f || a < Math.abs(end)) {
            a = end;
            s = p / 4.0f;
        } else {
            s = (float) (p / 6.283185307179586 * Math.asin(end / a));
        }
        return (float) (-(a * Math.pow(2.0, 10.0f * --value) * Math.sin((value * d - s) * 6.283185307179586 / p))
                + start);
    }

    public static float EaseOutElastic(float start, float end, float value) {
        end -= start;
        float d = 1.0f;
        float p = d * 0.3f;
        float a = 0.0f;
        if (value == 0.0f) {
            return start;
        }
        if ((value /= d) == 1.0f) {
            return start + end;
        }
        float s;
        if (a == 0.0f || a < Math.abs(end)) {
            a = end;
            s = p * 0.25f;
        } else {
            s = (float) (p / 6.283185307179586 * Math.asin(end / a));
        }
        return (float) (a * Math.pow(2.0, -10.0f * value) * Math.sin((value * d - s) * 6.283185307179586 / p) + end
                + start);
    }

    public static float EaseInOutElastic(float start, float end, float value) {
        end -= start;
        float d = 1.0f;
        float p = d * 0.3f;
        float a = 0.0f;
        if (value == 0.0f) {
            return start;
        }
        if ((value /= d * 0.5f) == 2.0f) {
            return start + end;
        }
        float s;
        if (a == 0.0f || a < Math.abs(end)) {
            a = end;
            s = p / 4.0f;
        } else {
            s = (float) (p / 6.283185307179586 * Math.asin(end / a));
        }
        if (value < 1.0f) {
            return (float) (-0.5
                    * (a * Math.pow(2.0, 10.0f * --value) * Math.sin((value * d - s) * 6.283185307179586 / p)) + start);
        }
        return (float) (a * Math.pow(2.0, -10.0f * --value) * Math.sin((value * d - s) * 6.283185307179586 / p) * 0.5
                + end + start);
    }

    public static float LinearD(float start, float end, float value) {
        return end - start;
    }

    public static float EaseInQuadD(float start, float end, float value) {
        return 2.0f * (end - start) * value;
    }

    public static float EaseOutQuadD(float start, float end, float value) {
        end -= start;
        return -end * value - end * (value - 2.0f);
    }

    public static float EaseInOutQuadD(float start, float end, float value) {
        value /= 0.5f;
        end -= start;
        if (value < 1.0f) {
            return end * value;
        }
        --value;
        return end * (1.0f - value);
    }

    public static float EaseInCubicD(float start, float end, float value) {
        return 3.0f * (end - start) * value * value;
    }

    public static float EaseOutCubicD(float start, float end, float value) {
        --value;
        end -= start;
        return 3.0f * end * value * value;
    }

    public static float EaseInOutCubicD(float start, float end, float value) {
        value /= 0.5f;
        end -= start;
        if (value < 1.0f) {
            return 1.5f * end * value * value;
        }
        value -= 2.0f;
        return 1.5f * end * value * value;
    }

    public static float EaseInQuartD(float start, float end, float value) {
        return 4.0f * (end - start) * value * value * value;
    }

    public static float EaseOutQuartD(float start, float end, float value) {
        --value;
        end -= start;
        return -4.0f * end * value * value * value;
    }

    public static float EaseInOutQuartD(float start, float end, float value) {
        value /= 0.5f;
        end -= start;
        if (value < 1.0f) {
            return 2.0f * end * value * value * value;
        }
        value -= 2.0f;
        return -2.0f * end * value * value * value;
    }

    public static float EaseInQuintD(float start, float end, float value) {
        return 5.0f * (end - start) * value * value * value * value;
    }

    public static float EaseOutQuintD(float start, float end, float value) {
        --value;
        end -= start;
        return 5.0f * end * value * value * value * value;
    }

    public static float EaseInOutQuintD(float start, float end, float value) {
        value /= 0.5f;
        end -= start;
        if (value < 1.0f) {
            return 2.5f * end * value * value * value * value;
        }
        value -= 2.0f;
        return 2.5f * end * value * value * value * value;
    }

    public static float EaseInSineD(float start, float end, float value) {
        return (float) ((end - start) * 0.5f * 3.141592653589793 * Math.sin(1.5707963267948966 * value));
    }

    public static float EaseOutSineD(float start, float end, float value) {
        end -= start;
        return (float) (1.5707963267948966 * end * Math.cos(value * 1.5707963267948966));
    }

    public static float EaseInOutSineD(float start, float end, float value) {
        end -= start;
        return (float) (end * 0.5f * 3.141592653589793 * Math.sin(3.141592653589793 * value));
    }

    public static float EaseInExpoD(float start, float end, float value) {
        return (float) (10.0f * EasingFunction.NATURAL_LOG_OF_2 * (end - start)
                * Math.pow(2.0, 10.0f * (value - 1.0f)));
    }

    public static float EaseOutExpoD(float start, float end, float value) {
        end -= start;
        return (float) (5.0f * EasingFunction.NATURAL_LOG_OF_2 * end * Math.pow(2.0, 1.0f - 10.0f * value));
    }

    public static float EaseInOutExpoD(float start, float end, float value) {
        value /= 0.5f;
        end -= start;
        if (value < 1.0f) {
            return (float) (5.0f * EasingFunction.NATURAL_LOG_OF_2 * end * Math.pow(2.0, 10.0f * (value - 1.0f)));
        }
        --value;
        return (float) (5.0f * EasingFunction.NATURAL_LOG_OF_2 * end / Math.pow(2.0, 10.0f * value));
    }

    public static float EaseInCircD(float start, float end, float value) {
        return (float) ((end - start) * value / Math.sqrt(1.0f - value * value));
    }

    public static float EaseOutCircD(float start, float end, float value) {
        --value;
        end -= start;
        return (float) (-end * value / Math.sqrt(1.0f - value * value));
    }

    public static float EaseInOutCircD(float start, float end, float value) {
        value /= 0.5f;
        end -= start;
        if (value < 1.0f) {
            return (float) (end * value / (2.0 * Math.sqrt(1.0f - value * value)));
        }
        value -= 2.0f;
        return (float) (-end * value / (2.0 * Math.sqrt(1.0f - value * value)));
    }

    public static float EaseInBounceD(float start, float end, float value) {
        end -= start;
        float d = 1.0f;
        return EaseOutBounceD(0.0f, end, d - value);
    }

    public static float EaseOutBounceD(float start, float end, float value) {
        value /= 1.0f;
        end -= start;
        if (value < 0.36363637f) {
            return 2.0f * end * 7.5625f * value;
        }
        if (value < 0.72727275f) {
            value -= 0.54545456f;
            return 2.0f * end * 7.5625f * value;
        }
        if (value < 0.9090909090909091) {
            value -= 0.8181818f;
            return 2.0f * end * 7.5625f * value;
        }
        value -= 0.95454544f;
        return 2.0f * end * 7.5625f * value;
    }

    public static float EaseInOutBounceD(float start, float end, float value) {
        end -= start;
        float d = 1.0f;
        if (value < d * 0.5f) {
            return EaseInBounceD(0.0f, end, value * 2.0f) * 0.5f;
        }
        return EaseOutBounceD(0.0f, end, value * 2.0f - d) * 0.5f;
    }

    public static float EaseInBackD(float start, float end, float value) {
        float s = 1.70158f;
        return 3.0f * (s + 1.0f) * (end - start) * value * value - 2.0f * s * (end - start) * value;
    }

    public static float EaseOutBackD(float start, float end, float value) {
        float s = 1.70158f;
        end -= start;
        --value;
        return end * ((s + 1.0f) * value * value + 2.0f * value * ((s + 1.0f) * value + s));
    }

    public static float EaseInOutBackD(float start, float end, float value) {
        float s = 1.70158f;
        end -= start;
        value /= 0.5f;
        if (value < 1.0f) {
            s *= 1.525f;
            return 0.5f * end * (s + 1.0f) * value * value + end * value * ((s + 1.0f) * value - s);
        }
        value -= 2.0f;
        s *= 1.525f;
        return 0.5f * end * ((s + 1.0f) * value * value + 2.0f * value * ((s + 1.0f) * value + s));
    }

    public static float EaseInElasticD(float start, float end, float value) {
        return EaseOutElasticD(start, end, 1.0f - value);
    }

    public static float EaseOutElasticD(float start, float end, float value) {
        end -= start;
        float d = 1.0f;
        float p = d * 0.3f;
        float a = 0.0f;
        float s;
        if (a == 0.0f || a < Math.abs(end)) {
            a = end;
            s = p * 0.25f;
        } else {
            s = (float) (p / 6.283185307179586 * Math.asin(end / a));
        }
        return (float) (a * 3.141592653589793 * d * Math.pow(2.0, 1.0f - 10.0f * value)
                * Math.cos(6.283185307179586 * (d * value - s) / p) / p
                - 5.0f * EasingFunction.NATURAL_LOG_OF_2 * a * Math.pow(2.0, 1.0f - 10.0f * value)
                        * Math.sin(6.283185307179586 * (d * value - s) / p));
    }

    public static float EaseInOutElasticD(float start, float end, float value) {
        end -= start;
        float d = 1.0f;
        float p = d * 0.3f;
        float a = 0.0f;
        float s;
        if (a == 0.0f || a < Math.abs(end)) {
            a = end;
            s = p / 4.0f;
        } else {
            s = (float) (p / 6.283185307179586 * Math.asin(end / a));
        }
        if (value < 1.0f) {
            --value;
            return (float) (-5.0f * EasingFunction.NATURAL_LOG_OF_2 * a * Math.pow(2.0, 10.0f * value)
                    * Math.sin(6.283185307179586 * (d * value - 2.0f) / p)
                    - a * 3.141592653589793 * d * Math.pow(2.0, 10.0f * value)
                            * Math.cos(6.283185307179586 * (d * value - s) / p) / p);
        }
        --value;
        return (float) (a * 3.141592653589793 * d * Math.cos(6.283185307179586 * (d * value - s) / p)
                / (p * Math.pow(2.0, 10.0f * value))
                - 5.0f * EasingFunction.NATURAL_LOG_OF_2 * a * Math.sin(6.283185307179586 * (d * value - s) / p)
                        / Math.pow(2.0, 10.0f * value));
    }

    public static float SpringD(float start, float end, float value) {
        value = CRes.Clamp01(value);
        end -= start;
        return (float) (end * (6.0f * (1.0f - value) / 5.0f + 1.0f)
                * (-2.200000047683716 * Math.pow(1.0f - value, 1.2000000476837158)
                        * Math.sin(3.141592653589793 * value * (2.5f * value * value * value + 0.2f))
                        + Math.pow(1.0f - value, 2.200000047683716)
                                * (3.141592653589793 * (2.5f * value * value * value + 0.2f)
                                        + 23.561944901923447 * value * value * value)
                                * Math.cos(3.141592653589793 * value * (2.5f * value * value * value + 0.2f))
                        + 1.0)
                - 6.0f * end
                        * (Math.pow(1.0f - value, 2.200000047683716)
                                * Math.sin(3.141592653589793 * value * (2.5f * value * value * value + 0.2f))
                                + value / 5.0f));
    }
}

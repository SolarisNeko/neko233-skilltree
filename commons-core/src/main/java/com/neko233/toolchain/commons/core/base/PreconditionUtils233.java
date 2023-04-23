package com.neko233.toolchain.commons.core.base;

import com.neko233.toolchain.commons.core.annotation.CanIgnoreReturnValue;
import com.neko233.toolchain.commons.core.annotation.NotNull;


/**
 * @author SolarisNeko
 * Date on 2023-01-28
 */
public final class PreconditionUtils233 {
    private PreconditionUtils233() {
    }

    public static void checkArgument(boolean expression) {
        if (!expression) {
            throw new IllegalArgumentException();
        }
    }

    public static void checkArgument(boolean expression,
                                     @NotNull Object errorMessage) {
        if (!expression) {
            throw new IllegalArgumentException(String.valueOf(errorMessage));
        }
    }

    public static void checkArgument(boolean expression,
                                     String errorMessageTemplate,
                                     @NotNull Object... errorMessageArgs) {
        if (!expression) {
            throw new IllegalArgumentException(StringUtils233.lenientFormat(errorMessageTemplate, errorMessageArgs));
        }
    }

    public static void checkArgument(boolean b,
                                     String errorMessageTemplate,
                                     char p1) {
        if (!b) {
            throw new IllegalArgumentException(StringUtils233.lenientFormat(errorMessageTemplate, new Object[]{p1}));
        }
    }

    public static void checkArgument(boolean b,
                                     String errorMessageTemplate,
                                     int p1) {
        if (!b) {
            throw new IllegalArgumentException(StringUtils233.lenientFormat(errorMessageTemplate, new Object[]{p1}));
        }
    }

    public static void checkArgument(boolean b,
                                     String errorMessageTemplate,
                                     long p1) {
        if (!b) {
            throw new IllegalArgumentException(StringUtils233.lenientFormat(errorMessageTemplate, new Object[]{p1}));
        }
    }

    public static void checkArgument(boolean b,
                                     String errorMessageTemplate,
                                     @NotNull Object p1) {
        if (!b) {
            throw new IllegalArgumentException(StringUtils233.lenientFormat(errorMessageTemplate, new Object[]{p1}));
        }
    }

    public static void checkArgument(boolean b,
                                     String errorMessageTemplate,
                                     char p1,
                                     char p2) {
        if (!b) {
            throw new IllegalArgumentException(StringUtils233.lenientFormat(errorMessageTemplate, new Object[]{p1, p2}));
        }
    }

    public static void checkArgument(boolean b,
                                     String errorMessageTemplate,
                                     char p1,
                                     int p2) {
        if (!b) {
            throw new IllegalArgumentException(StringUtils233.lenientFormat(errorMessageTemplate, new Object[]{p1, p2}));
        }
    }

    public static void checkArgument(boolean b,
                                     String errorMessageTemplate,
                                     char p1,
                                     long p2) {
        if (!b) {
            throw new IllegalArgumentException(StringUtils233.lenientFormat(errorMessageTemplate, new Object[]{p1, p2}));
        }
    }

    public static void checkArgument(boolean b,
                                     String errorMessageTemplate,
                                     char p1,
                                     @NotNull Object p2) {
        if (!b) {
            throw new IllegalArgumentException(StringUtils233.lenientFormat(errorMessageTemplate, new Object[]{p1, p2}));
        }
    }

    public static void checkArgument(boolean b,
                                     String errorMessageTemplate,
                                     int p1,
                                     char p2) {
        if (!b) {
            throw new IllegalArgumentException(StringUtils233.lenientFormat(errorMessageTemplate, new Object[]{p1, p2}));
        }
    }

    public static void checkArgument(boolean b,
                                     String errorMessageTemplate,
                                     int p1,
                                     int p2) {
        if (!b) {
            throw new IllegalArgumentException(StringUtils233.lenientFormat(errorMessageTemplate, new Object[]{p1, p2}));
        }
    }

    public static void checkArgument(boolean b,
                                     String errorMessageTemplate,
                                     int p1,
                                     long p2) {
        if (!b) {
            throw new IllegalArgumentException(StringUtils233.lenientFormat(errorMessageTemplate, new Object[]{p1, p2}));
        }
    }

    public static void checkArgument(boolean b,
                                     String errorMessageTemplate,
                                     int p1,
                                     @NotNull Object p2) {
        if (!b) {
            throw new IllegalArgumentException(StringUtils233.lenientFormat(errorMessageTemplate, new Object[]{p1, p2}));
        }
    }

    public static void checkArgument(boolean b,
                                     String errorMessageTemplate,
                                     long p1,
                                     char p2) {
        if (!b) {
            throw new IllegalArgumentException(StringUtils233.lenientFormat(errorMessageTemplate, new Object[]{p1, p2}));
        }
    }

    public static void checkArgument(boolean b,
                                     String errorMessageTemplate,
                                     long p1,
                                     int p2) {
        if (!b) {
            throw new IllegalArgumentException(StringUtils233.lenientFormat(errorMessageTemplate, new Object[]{p1, p2}));
        }
    }

    public static void checkArgument(boolean b,
                                     String errorMessageTemplate,
                                     long p1,
                                     long p2) {
        if (!b) {
            throw new IllegalArgumentException(StringUtils233.lenientFormat(errorMessageTemplate, new Object[]{p1, p2}));
        }
    }

    public static void checkArgument(boolean b,
                                     String errorMessageTemplate,
                                     long p1,
                                     @NotNull Object p2) {
        if (!b) {
            throw new IllegalArgumentException(StringUtils233.lenientFormat(errorMessageTemplate, new Object[]{p1, p2}));
        }
    }

    public static void checkArgument(boolean b,
                                     String errorMessageTemplate,
                                     @NotNull Object p1,
                                     char p2) {
        if (!b) {
            throw new IllegalArgumentException(StringUtils233.lenientFormat(errorMessageTemplate, new Object[]{p1, p2}));
        }
    }

    public static void checkArgument(boolean b,
                                     String errorMessageTemplate,
                                     @NotNull Object p1,
                                     int p2) {
        if (!b) {
            throw new IllegalArgumentException(StringUtils233.lenientFormat(errorMessageTemplate, new Object[]{p1, p2}));
        }
    }

    public static void checkArgument(boolean b,
                                     String errorMessageTemplate,
                                     @NotNull Object p1,
                                     long p2) {
        if (!b) {
            throw new IllegalArgumentException(StringUtils233.lenientFormat(errorMessageTemplate, new Object[]{p1, p2}));
        }
    }

    public static void checkArgument(boolean b,
                                     String errorMessageTemplate,
                                     @NotNull Object p1,
                                     @NotNull Object p2) {
        if (!b) {
            throw new IllegalArgumentException(StringUtils233.lenientFormat(errorMessageTemplate, new Object[]{p1, p2}));
        }
    }

    public static void checkArgument(boolean b,
                                     String errorMessageTemplate,
                                     @NotNull Object p1,
                                     @NotNull Object p2,
                                     @NotNull Object p3) {
        if (!b) {
            throw new IllegalArgumentException(StringUtils233.lenientFormat(errorMessageTemplate, new Object[]{p1, p2, p3}));
        }
    }

    public static void checkArgument(boolean b,
                                     String errorMessageTemplate,
                                     @NotNull Object p1,
                                     @NotNull Object p2,
                                     @NotNull Object p3,
                                     @NotNull Object p4) {
        if (!b) {
            throw new IllegalArgumentException(StringUtils233.lenientFormat(errorMessageTemplate, new Object[]{p1, p2, p3, p4}));
        }
    }

    public static void checkState(boolean expression) {
        if (!expression) {
            throw new IllegalStateException();
        }
    }

    public static void checkState(boolean expression,
                                  @NotNull Object errorMessage) {
        if (!expression) {
            throw new IllegalStateException(String.valueOf(errorMessage));
        }
    }

    public static void checkState(boolean expression,
                                  @NotNull String errorMessageTemplate,
                                  @NotNull Object... errorMessageArgs) {
        if (!expression) {
            throw new IllegalStateException(StringUtils233.lenientFormat(errorMessageTemplate, errorMessageArgs));
        }
    }

    public static void checkState(boolean b,
                                  String errorMessageTemplate,
                                  char p1) {
        if (!b) {
            throw new IllegalStateException(StringUtils233.lenientFormat(errorMessageTemplate, new Object[]{p1}));
        }
    }

    public static void checkState(boolean b,
                                  String errorMessageTemplate,
                                  int p1) {
        if (!b) {
            throw new IllegalStateException(StringUtils233.lenientFormat(errorMessageTemplate, new Object[]{p1}));
        }
    }

    public static void checkState(boolean b,
                                  String errorMessageTemplate,
                                  long p1) {
        if (!b) {
            throw new IllegalStateException(StringUtils233.lenientFormat(errorMessageTemplate, new Object[]{p1}));
        }
    }

    public static void checkState(boolean b,
                                  String errorMessageTemplate,
                                  @NotNull Object p1) {
        if (!b) {
            throw new IllegalStateException(StringUtils233.lenientFormat(errorMessageTemplate, new Object[]{p1}));
        }
    }

    public static void checkState(boolean b,
                                  String errorMessageTemplate,
                                  char p1,
                                  char p2) {
        if (!b) {
            throw new IllegalStateException(StringUtils233.lenientFormat(errorMessageTemplate, new Object[]{p1, p2}));
        }
    }

    public static void checkState(boolean b,
                                  String errorMessageTemplate,
                                  char p1,
                                  int p2) {
        if (!b) {
            throw new IllegalStateException(StringUtils233.lenientFormat(errorMessageTemplate, new Object[]{p1, p2}));
        }
    }

    public static void checkState(boolean b,
                                  String errorMessageTemplate,
                                  char p1,
                                  long p2) {
        if (!b) {
            throw new IllegalStateException(StringUtils233.lenientFormat(errorMessageTemplate, new Object[]{p1, p2}));
        }
    }

    public static void checkState(boolean b,
                                  String errorMessageTemplate,
                                  char p1,
                                  @NotNull Object p2) {
        if (!b) {
            throw new IllegalStateException(StringUtils233.lenientFormat(errorMessageTemplate, new Object[]{p1, p2}));
        }
    }

    public static void checkState(boolean b,
                                  String errorMessageTemplate,
                                  int p1,
                                  char p2) {
        if (!b) {
            throw new IllegalStateException(StringUtils233.lenientFormat(errorMessageTemplate, new Object[]{p1, p2}));
        }
    }

    public static void checkState(boolean b,
                                  String errorMessageTemplate,
                                  int p1,
                                  int p2) {
        if (!b) {
            throw new IllegalStateException(StringUtils233.lenientFormat(errorMessageTemplate, new Object[]{p1, p2}));
        }
    }

    public static void checkState(boolean b,
                                  String errorMessageTemplate,
                                  int p1,
                                  long p2) {
        if (!b) {
            throw new IllegalStateException(StringUtils233.lenientFormat(errorMessageTemplate, new Object[]{p1, p2}));
        }
    }

    public static void checkState(boolean b,
                                  String errorMessageTemplate,
                                  int p1,
                                  @NotNull Object p2) {
        if (!b) {
            throw new IllegalStateException(StringUtils233.lenientFormat(errorMessageTemplate, new Object[]{p1, p2}));
        }
    }

    public static void checkState(boolean b,
                                  String errorMessageTemplate,
                                  long p1,
                                  char p2) {
        if (!b) {
            throw new IllegalStateException(StringUtils233.lenientFormat(errorMessageTemplate, new Object[]{p1, p2}));
        }
    }

    public static void checkState(boolean b,
                                  String errorMessageTemplate,
                                  long p1,
                                  int p2) {
        if (!b) {
            throw new IllegalStateException(StringUtils233.lenientFormat(errorMessageTemplate, new Object[]{p1, p2}));
        }
    }

    public static void checkState(boolean b,
                                  String errorMessageTemplate,
                                  long p1,
                                  long p2) {
        if (!b) {
            throw new IllegalStateException(StringUtils233.lenientFormat(errorMessageTemplate, new Object[]{p1, p2}));
        }
    }

    public static void checkState(boolean b,
                                  String errorMessageTemplate,
                                  long p1,
                                  @NotNull Object p2) {
        if (!b) {
            throw new IllegalStateException(StringUtils233.lenientFormat(errorMessageTemplate, new Object[]{p1, p2}));
        }
    }

    public static void checkState(boolean b,
                                  String errorMessageTemplate,
                                  @NotNull Object p1,
                                  char p2) {
        if (!b) {
            throw new IllegalStateException(StringUtils233.lenientFormat(errorMessageTemplate, new Object[]{p1, p2}));
        }
    }

    public static void checkState(boolean b,
                                  String errorMessageTemplate,
                                  @NotNull Object p1,
                                  int p2) {
        if (!b) {
            throw new IllegalStateException(StringUtils233.lenientFormat(errorMessageTemplate, new Object[]{p1, p2}));
        }
    }

    public static void checkState(boolean b,
                                  String errorMessageTemplate,
                                  @NotNull Object p1,
                                  long p2) {
        if (!b) {
            throw new IllegalStateException(StringUtils233.lenientFormat(errorMessageTemplate, new Object[]{p1, p2}));
        }
    }

    public static void checkState(boolean b,
                                  String errorMessageTemplate,
                                  @NotNull Object p1,
                                  @NotNull Object p2) {
        if (!b) {
            throw new IllegalStateException(StringUtils233.lenientFormat(errorMessageTemplate, new Object[]{p1, p2}));
        }
    }

    public static void checkState(boolean b,
                                  String errorMessageTemplate,
                                  @NotNull Object p1,
                                  @NotNull Object p2,
                                  @NotNull Object p3) {
        if (!b) {
            throw new IllegalStateException(StringUtils233.lenientFormat(errorMessageTemplate, new Object[]{p1, p2, p3}));
        }
    }

    public static void checkState(boolean b,
                                  String errorMessageTemplate,
                                  @NotNull Object p1,
                                  @NotNull Object p2,
                                  @NotNull Object p3,
                                  @NotNull Object p4) {
        if (!b) {
            throw new IllegalStateException(StringUtils233.lenientFormat(errorMessageTemplate, new Object[]{p1, p2, p3, p4}));
        }
    }

    @CanIgnoreReturnValue
    public static <T> T checkNotNull(@NotNull T reference) {
        if (reference == null) {
            throw new NullPointerException();
        } else {
            return reference;
        }
    }

    @CanIgnoreReturnValue
    public static <T> T checkNotNull(@NotNull T reference,
                                     @NotNull Object errorMessage) {
        if (reference == null) {
            throw new NullPointerException(String.valueOf(errorMessage));
        } else {
            return reference;
        }
    }

    @CanIgnoreReturnValue
    public static <T> T checkNotNull(@NotNull T reference,
                                     String errorMessageTemplate,
                                     @NotNull Object... errorMessageArgs) {
        if (reference == null) {
            throw new NullPointerException(StringUtils233.lenientFormat(errorMessageTemplate, errorMessageArgs));
        } else {
            return reference;
        }
    }

    @CanIgnoreReturnValue
    public static <T> T checkNotNull(@NotNull T obj,
                                     String errorMessageTemplate,
                                     char p1) {
        if (obj == null) {
            throw new NullPointerException(StringUtils233.lenientFormat(errorMessageTemplate, new Object[]{p1}));
        } else {
            return obj;
        }
    }

    @CanIgnoreReturnValue
    public static <T> T checkNotNull(@NotNull T obj,
                                     String errorMessageTemplate,
                                     int p1) {
        if (obj == null) {
            throw new NullPointerException(StringUtils233.lenientFormat(errorMessageTemplate, new Object[]{p1}));
        } else {
            return obj;
        }
    }

    @CanIgnoreReturnValue
    public static <T> T checkNotNull(@NotNull T obj,
                                     String errorMessageTemplate,
                                     long p1) {
        if (obj == null) {
            throw new NullPointerException(StringUtils233.lenientFormat(errorMessageTemplate, new Object[]{p1}));
        } else {
            return obj;
        }
    }

    @CanIgnoreReturnValue
    public static <T> T checkNotNull(@NotNull T obj,
                                     String errorMessageTemplate,
                                     @NotNull Object p1) {
        if (obj == null) {
            throw new NullPointerException(StringUtils233.lenientFormat(errorMessageTemplate, new Object[]{p1}));
        } else {
            return obj;
        }
    }

    @CanIgnoreReturnValue
    public static <T> T checkNotNull(@NotNull T obj,
                                     String errorMessageTemplate,
                                     char p1,
                                     char p2) {
        if (obj == null) {
            throw new NullPointerException(StringUtils233.lenientFormat(errorMessageTemplate, new Object[]{p1, p2}));
        } else {
            return obj;
        }
    }

    @CanIgnoreReturnValue
    public static <T> T checkNotNull(@NotNull T obj,
                                     String errorMessageTemplate,
                                     char p1,
                                     int p2) {
        if (obj == null) {
            throw new NullPointerException(StringUtils233.lenientFormat(errorMessageTemplate, new Object[]{p1, p2}));
        } else {
            return obj;
        }
    }

    @CanIgnoreReturnValue
    public static <T> T checkNotNull(@NotNull T obj,
                                     String errorMessageTemplate,
                                     char p1,
                                     long p2) {
        if (obj == null) {
            throw new NullPointerException(StringUtils233.lenientFormat(errorMessageTemplate, new Object[]{p1, p2}));
        } else {
            return obj;
        }
    }

    @CanIgnoreReturnValue
    public static <T> T checkNotNull(@NotNull T obj,
                                     String errorMessageTemplate,
                                     char p1,
                                     @NotNull Object p2) {
        if (obj == null) {
            throw new NullPointerException(StringUtils233.lenientFormat(errorMessageTemplate, new Object[]{p1, p2}));
        } else {
            return obj;
        }
    }

    @CanIgnoreReturnValue
    public static <T> T checkNotNull(@NotNull T obj,
                                     String errorMessageTemplate,
                                     int p1,
                                     char p2) {
        if (obj == null) {
            throw new NullPointerException(StringUtils233.lenientFormat(errorMessageTemplate, new Object[]{p1, p2}));
        } else {
            return obj;
        }
    }

    @CanIgnoreReturnValue
    public static <T> T checkNotNull(@NotNull T obj,
                                     String errorMessageTemplate,
                                     int p1,
                                     int p2) {
        if (obj == null) {
            throw new NullPointerException(StringUtils233.lenientFormat(errorMessageTemplate, new Object[]{p1, p2}));
        } else {
            return obj;
        }
    }

    @CanIgnoreReturnValue
    public static <T> T checkNotNull(@NotNull T obj,
                                     String errorMessageTemplate,
                                     int p1,
                                     long p2) {
        if (obj == null) {
            throw new NullPointerException(StringUtils233.lenientFormat(errorMessageTemplate, new Object[]{p1, p2}));
        } else {
            return obj;
        }
    }

    @CanIgnoreReturnValue
    public static <T> T checkNotNull(@NotNull T obj,
                                     String errorMessageTemplate,
                                     int p1,
                                     @NotNull Object p2) {
        if (obj == null) {
            throw new NullPointerException(StringUtils233.lenientFormat(errorMessageTemplate, new Object[]{p1, p2}));
        } else {
            return obj;
        }
    }

    @CanIgnoreReturnValue
    public static <T> T checkNotNull(@NotNull T obj,
                                     String errorMessageTemplate,
                                     long p1,
                                     char p2) {
        if (obj == null) {
            throw new NullPointerException(StringUtils233.lenientFormat(errorMessageTemplate, new Object[]{p1, p2}));
        } else {
            return obj;
        }
    }

    @CanIgnoreReturnValue
    public static <T> T checkNotNull(@NotNull T obj,
                                     String errorMessageTemplate,
                                     long p1,
                                     int p2) {
        if (obj == null) {
            throw new NullPointerException(StringUtils233.lenientFormat(errorMessageTemplate, new Object[]{p1, p2}));
        } else {
            return obj;
        }
    }

    @CanIgnoreReturnValue
    public static <T> T checkNotNull(@NotNull T obj,
                                     String errorMessageTemplate,
                                     long p1,
                                     long p2) {
        if (obj == null) {
            throw new NullPointerException(StringUtils233.lenientFormat(errorMessageTemplate, new Object[]{p1, p2}));
        } else {
            return obj;
        }
    }

    @CanIgnoreReturnValue
    public static <T> T checkNotNull(@NotNull T obj,
                                     String errorMessageTemplate,
                                     long p1,
                                     @NotNull Object p2) {
        if (obj == null) {
            throw new NullPointerException(StringUtils233.lenientFormat(errorMessageTemplate, new Object[]{p1, p2}));
        } else {
            return obj;
        }
    }

    @CanIgnoreReturnValue
    public static <T> T checkNotNull(@NotNull T obj,
                                     String errorMessageTemplate,
                                     @NotNull Object p1,
                                     char p2) {
        if (obj == null) {
            throw new NullPointerException(StringUtils233.lenientFormat(errorMessageTemplate, new Object[]{p1, p2}));
        } else {
            return obj;
        }
    }

    @CanIgnoreReturnValue
    public static <T> T checkNotNull(@NotNull T obj,
                                     String errorMessageTemplate,
                                     @NotNull Object p1,
                                     int p2) {
        if (obj == null) {
            throw new NullPointerException(StringUtils233.lenientFormat(errorMessageTemplate, new Object[]{p1, p2}));
        } else {
            return obj;
        }
    }

    @CanIgnoreReturnValue
    public static <T> T checkNotNull(@NotNull T obj,
                                     String errorMessageTemplate,
                                     @NotNull Object p1,
                                     long p2) {
        if (obj == null) {
            throw new NullPointerException(StringUtils233.lenientFormat(errorMessageTemplate, new Object[]{p1, p2}));
        } else {
            return obj;
        }
    }

    @CanIgnoreReturnValue
    public static <T> T checkNotNull(@NotNull T obj,
                                     String errorMessageTemplate,
                                     @NotNull Object p1,
                                     @NotNull Object p2) {
        if (obj == null) {
            throw new NullPointerException(StringUtils233.lenientFormat(errorMessageTemplate, new Object[]{p1, p2}));
        } else {
            return obj;
        }
    }

    @CanIgnoreReturnValue
    public static <T> T checkNotNull(@NotNull T obj,
                                     String errorMessageTemplate,
                                     @NotNull Object p1,
                                     @NotNull Object p2,
                                     @NotNull Object p3) {
        if (obj == null) {
            throw new NullPointerException(StringUtils233.lenientFormat(errorMessageTemplate, new Object[]{p1, p2, p3}));
        } else {
            return obj;
        }
    }

    @CanIgnoreReturnValue
    public static <T> T checkNotNull(@NotNull T obj,
                                     String errorMessageTemplate,
                                     @NotNull Object p1,
                                     @NotNull Object p2,
                                     @NotNull Object p3,
                                     @NotNull Object p4) {
        if (obj == null) {
            throw new NullPointerException(StringUtils233.lenientFormat(errorMessageTemplate, new Object[]{p1, p2, p3, p4}));
        } else {
            return obj;
        }
    }

    @CanIgnoreReturnValue
    public static int checkElementIndex(int index,
                                        int size) {
        return checkElementIndex(index, size, "index");
    }

    @CanIgnoreReturnValue
    public static int checkElementIndex(int index,
                                        int size,
                                        String desc) {
        if (index >= 0 && index < size) {
            return index;
        } else {
            throw new IndexOutOfBoundsException(badElementIndex(index, size, desc));
        }
    }

    private static String badElementIndex(int index,
                                          int size,
                                          String desc) {
        if (index < 0) {
            return StringUtils233.lenientFormat("%s (%s) must not be negative", new Object[]{desc, index});
        } else if (size < 0) {
            throw new IllegalArgumentException((new StringBuilder(26)).append("negative size: ").append(size).toString());
        } else {
            return StringUtils233.lenientFormat("%s (%s) must be less than size (%s)", new Object[]{desc, index, size});
        }
    }

    @CanIgnoreReturnValue
    public static int checkPositionIndex(int index,
                                         int size) {
        return checkPositionIndex(index, size, "index");
    }

    @CanIgnoreReturnValue
    public static int checkPositionIndex(int index,
                                         int size,
                                         String desc) {
        if (index >= 0 && index <= size) {
            return index;
        } else {
            throw new IndexOutOfBoundsException(badPositionIndex(index, size, desc));
        }
    }

    private static String badPositionIndex(int index,
                                           int size,
                                           String desc) {
        if (index < 0) {
            return StringUtils233.lenientFormat("%s (%s) must not be negative", new Object[]{desc, index});
        } else if (size < 0) {
            throw new IllegalArgumentException((new StringBuilder(26)).append("negative size: ").append(size).toString());
        } else {
            return StringUtils233.lenientFormat("%s (%s) must not be greater than size (%s)", new Object[]{desc, index, size});
        }
    }

    public static void checkPositionIndexes(int start,
                                            int end,
                                            int size) {
        if (start < 0 || end < start || end > size) {
            throw new IndexOutOfBoundsException(badPositionIndexes(start, end, size));
        }
    }

    private static String badPositionIndexes(int start,
                                             int end,
                                             int size) {
        if (start >= 0 && start <= size) {
            return end >= 0 && end <= size ? StringUtils233.lenientFormat("end index (%s) must not be less than start index (%s)", new Object[]{end, start}) : badPositionIndex(end, size, "end index");
        } else {
            return badPositionIndex(start, size, "start index");
        }
    }
}

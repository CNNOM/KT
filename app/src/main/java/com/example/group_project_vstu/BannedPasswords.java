package com.example.group_project_vstu;

public enum BannedPasswords {
    PASSWORD("password"),
    QWERTY("qwerty"),
    ABC123("abc123"),
    PASSWORD1("password1"),
    PASSWORD2("password2"),
    PASSWORD3("password3"),
    PASSWORD4("password4"),
    PASSWORD5("password5"),
    PASSWORD6("password6"),
    PASSWORD7("password7"),
    PASSWORD8("password8"),
    PASSWORD9("password9"),
    PASSWORD10("password10"),
    ADMIN("admin"),
    LETMEIN("letmein"),
    WELCOME("welcome"),
    MONKEY("monkey"),
    SUNSHINE("sunshine"),
    ILOVEYOU("iloveyou"),
    PRINCESS("princess"),
    FOOTBALL("football"),
    ADMIN123("admin123"),
    PASSWORD123("password123"),
    QWERTY123("qwerty123"),
    QWERTYUIOP("qwertyuiop"),
    ASDFGHJKL("asdfghjkl"),
    ZXCVBNM("zxcvbnm"),
    QAZWSX("qazwsx"),
    ZAQ12WSX("zaq12wsx"),
    QWE123("qwe123"),
    ASDF1234("asdf1234"),
    ZXCV1234("zxcv1234"),
    QWERT12345("qwert12345"),
    ASDFG12345("asdfg12345"),
    ZXCVB12345("zxcvb12345"),
    QWERTY123456("qwerty123456"),
    ASDFGH123456("asdfgh123456"),
    ZXCVBN123456("zxcvbn123456");

    private final String password;

    BannedPasswords(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public static boolean contains(String password) {
        for (BannedPasswords bannedPassword : BannedPasswords.values()) {
            if (bannedPassword.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }
}
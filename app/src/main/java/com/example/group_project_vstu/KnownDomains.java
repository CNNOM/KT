package com.example.group_project_vstu;

public enum KnownDomains {
    GMAIL("gmail.com"),
    YAHOO("yahoo.com"),
    HOTMAIL("hotmail.com"),
    OUTLOOK("outlook.com"),
    YANDEX("yandex.ru"),
    YA("ya.ru"),
    MAIL_RU("mail.ru"),
    ICLOUD("icloud.com"),
    PROTONMAIL("protonmail.com"),
    AOL("aol.com"),
    ZOHO("zoho.com"),
    GMX("gmx.com"),
    INBOX("inbox.com"),
    MAIL_COM("mail.com"),
    YOPMAIL("yopmail.com"),
    MAILINATOR("mailinator.com"),
    TEMPMAIL("tempmail.com"),
    TEMP_EMAIL("temp-email.com"),
    TEMP_MAIL("temp-mail.org"),
    DISPOSTABLE("dispostable.com");

    private final String domain;

    KnownDomains(String domain) {
        this.domain = domain;
    }

    public String getDomain() {
        return domain;
    }

    public static boolean contains(String domain) {
        for (KnownDomains knownDomain : KnownDomains.values()) {
            if (knownDomain.getDomain().equals(domain)) {
                return true;
            }
        }
        return false;
    }
}
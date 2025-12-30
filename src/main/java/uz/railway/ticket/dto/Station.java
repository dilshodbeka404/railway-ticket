package uz.railway.ticket.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Station {

    ANDIJAN("Andijon", "2900680"),
    BUKHARA("Buxoro", "2900800"),
    GULISTON("Guliston", "2900850"),
    JIZZAKH("Jizzax", "2900720"),
    MARGILAN("Margilon", "2900920"),
    NAMANGAN("Namangan", "2900940"),
    NAVOIY("Navoiy", "2900930"),
    NUKUS("Nukus", "2900970"),
    PAP("Pop", "2900693"),
    QARSHI("Qarshi", "2900750"),
    KOKAND("Qo'qon", "2900880"),
    SAMARKAND("Samarqand", "2900700"),
    TASHKENT("TOSHKENT", "2900000"),
    TERMEZ("Termiz", "2900255"),
    KHIVA("Xiva", "2900172"),
    MISKIN("MISKIN", "2900104"),
    URGENCH("URGANCH", "2900790");

    private final String name;
    private final String code;
}

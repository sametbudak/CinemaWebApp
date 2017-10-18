package org.kiev.cinema.dto.admins;

import org.kiev.cinema.enums.GenreEnum;

public class CheckedGenre {

    private GenreEnum genreEnum;
    private String checked;

    public CheckedGenre(GenreEnum genreEnum, boolean isChecked) {
        this.genreEnum = genreEnum;
        this.checked = isChecked ? "checked" : "" ;
    }

    public GenreEnum getGenreEnum() {
        return genreEnum;
    }

    public String getChecked() {
        return checked;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        CheckedGenre that = (CheckedGenre) obj;

        return this.genreEnum == that.genreEnum;
    }

    @Override
    public int hashCode() {
        return this.genreEnum.hashCode();
    }

}

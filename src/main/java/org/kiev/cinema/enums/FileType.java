package org.kiev.cinema.enums;

import org.kiev.cinema.CinemaConstants;

import java.util.Arrays;

public enum FileType {
    VIDEO {
        @Override
        public boolean isMimeTypeAllowed(String mime) {
            int mimeInd = Arrays.binarySearch(CinemaConstants.VIDEO_MIME, mime);
            return mimeInd >= 0;
        }
    },
    IMG {
        @Override
        public boolean isMimeTypeAllowed(String mime) {
            int mimeInd = Arrays.binarySearch(CinemaConstants.IMG_MIME, mime);
            return mimeInd >= 0;
        }
    };

    FileType() {
    }
    public abstract boolean isMimeTypeAllowed(String mime);
}

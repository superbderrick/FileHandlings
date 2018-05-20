package io.github.superbderrick.kotlinvideoplayer.AudioPlayer;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public abstract class PlaybackInfoListener {
    //@IntDef: Limit the range
    @IntDef({State.INVALID, State.PLAYING, State.PAUSED, State.RESET, State.COMPLETED})

    //@Retention(): Range of Annotation
    @Retention(RetentionPolicy.SOURCE)
    @interface State {
        int INVALID = -1;
        int PLAYING = 0;
        int PAUSED = 1;
        int RESET = 2;
        int COMPLETED = 3;
    }

    public static String convertStateToString(@State int state) {
        String stateString;
        switch (state) {
            case State.COMPLETED:
                stateString = "COMPLETED";
                break;
            case State.INVALID:
                stateString = "INVALID";
                break;
            case State.PAUSED:
                stateString = "PAUSED";
                break;
            case State.PLAYING:
                stateString = "PLAYING";
                break;
            case State.RESET:
                stateString = "RESET";
                break;
            default:
                stateString = "N/A";
        }
        return stateString;
    }

    public void onLogUpdated(String formattedMessage) {
    }

    public void onDurationChanged(int duration) {
    }

    public void onPositionChanged(int position) {
    }

    public void onStateChanged(@State int state) {
    }

    public void onPlaybackCompleted() {
    }
}

package snow.player.playlist;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.common.base.Preconditions;
import com.tencent.mmkv.MMKV;

import snow.player.media.MusicItem;
import snow.player.Player;

/**
 * 用于对播放队列的状态进行持久化。
 */
public class PersistentPlaylistState extends PlaylistState {
    private static final String KEY_PLAY_PROGRESS = "play_progress";
    private static final String KEY_PLAY_PROGRESS_UPDATE_TIME = "play_progress_update_time";
    private static final String KEY_SOUND_QUALITY = "sound_quality";
    private static final String KEY_AUDIO_EFFECT_ENABLED = "audio_effect_enabled";
    private static final String KEY_ONLY_WIFI_NETWORK = "only_wifi_network";
    private static final String KEY_IGNORE_LOSS_AUDIO_FOCUS = "ignore_loss_audio_focus";
    private static final String KEY_MUSIC_ITEM = "music_item";

    private static final String KEY_POSITION = "position";
    private static final String KEY_PLAY_MODE = "play_mode";

    private MMKV mMMKV;

    public PersistentPlaylistState(@NonNull Context context, @NonNull String id) {
        Preconditions.checkNotNull(context);
        Preconditions.checkNotNull(id);

        MMKV.initialize(context);

        mMMKV = MMKV.mmkvWithID(id);

        super.setPlayProgress(mMMKV.decodeInt(KEY_PLAY_PROGRESS, 0));
        super.setPlayProgressUpdateTime(mMMKV.decodeLong(KEY_PLAY_PROGRESS_UPDATE_TIME, System.currentTimeMillis()));
        super.setSoundQuality(Player.SoundQuality.values()[mMMKV.decodeInt(KEY_SOUND_QUALITY, 0)]);
        super.setAudioEffectEnabled(mMMKV.decodeBool(KEY_AUDIO_EFFECT_ENABLED, false));
        super.setOnlyWifiNetwork(mMMKV.decodeBool(KEY_ONLY_WIFI_NETWORK, true));
        super.setIgnoreLossAudioFocus(mMMKV.decodeBool(KEY_IGNORE_LOSS_AUDIO_FOCUS, false));
        super.setMusicItem(mMMKV.decodeParcelable(KEY_MUSIC_ITEM, MusicItem.class));

        super.setPosition(mMMKV.decodeInt(KEY_POSITION, 0));
        super.setPlayMode(PlaylistPlayer.PlayMode.values()[mMMKV.decodeInt(KEY_PLAY_MODE, 0)]);
    }

    @Override
    public void setPlayProgress(int playProgress) {
        super.setPlayProgress(playProgress);

        mMMKV.encode(KEY_PLAY_PROGRESS, playProgress);
    }

    @Override
    public void setPlayProgressUpdateTime(long updateTime) {
        super.setPlayProgressUpdateTime(updateTime);

        mMMKV.encode(KEY_PLAY_PROGRESS_UPDATE_TIME, updateTime);
    }

    @Override
    public void setSoundQuality(@NonNull Player.SoundQuality soundQuality) {
        super.setSoundQuality(soundQuality);

        mMMKV.encode(KEY_SOUND_QUALITY, soundQuality.ordinal());
    }

    @Override
    public void setAudioEffectEnabled(boolean audioEffectEnabled) {
        super.setAudioEffectEnabled(audioEffectEnabled);

        mMMKV.encode(KEY_AUDIO_EFFECT_ENABLED, audioEffectEnabled);
    }

    @Override
    public void setOnlyWifiNetwork(boolean onlyWifiNetwork) {
        super.setOnlyWifiNetwork(onlyWifiNetwork);

        mMMKV.encode(KEY_ONLY_WIFI_NETWORK, onlyWifiNetwork);
    }

    @Override
    public void setIgnoreLossAudioFocus(boolean ignoreLossAudioFocus) {
        super.setIgnoreLossAudioFocus(ignoreLossAudioFocus);

        mMMKV.encode(KEY_IGNORE_LOSS_AUDIO_FOCUS, ignoreLossAudioFocus);
    }

    @Override
    public void setPosition(int position) {
        super.setPosition(position);

        mMMKV.encode(KEY_POSITION, position);
    }

    @Override
    public void setPlayMode(@NonNull PlaylistPlayer.PlayMode playMode) {
        super.setPlayMode(playMode);

        mMMKV.encode(KEY_PLAY_MODE, playMode.ordinal());
    }

    @Override
    public void setMusicItem(@Nullable MusicItem musicItem) {
        super.setMusicItem(musicItem);

        if (musicItem == null) {
            mMMKV.remove(KEY_MUSIC_ITEM);
            return;
        }

        mMMKV.encode(KEY_MUSIC_ITEM, musicItem);
    }

    public PlaylistState getPlaylistState() {
        return new PlaylistState(this);
    }
}
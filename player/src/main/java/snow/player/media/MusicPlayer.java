package snow.player.media;

import android.net.Uri;

/**
 * 该接口定义了音乐播放器的基本功能。可以通过实现该接口来创建一个自定义的音乐播放器。
 *
 * @see MediaMusicPlayer
 */
public interface MusicPlayer {

    /**
     * 准备当前音乐播放器。
     * <p>
     * 该方法会在主线程上执行，如果准备操作是个耗时操作，你应该在异步线程中执行它。
     * <p>
     * 在实现该方法时，建议先检查 {@link #isInvalid()} 状态，如果返回 {@code true}，说明当前
     * MusicPlayer 已失效，此时因立即从该方法中返回，不应该再调用任何方法。具体做法请参照
     * {@link MediaMusicPlayer} 的源码。
     *
     * @param uri 要播放的音乐的 URI
     */
    void prepare(Uri uri) throws Exception;

    /**
     * 设置是否循环播放。
     *
     * @param looping 是否循环播放（默认为 false）。
     */
    void setLooping(boolean looping);

    /**
     * 判断是否循环播放（默认为 false）。
     *
     * @return 是否循环播放（默认为 false）。
     */
    boolean isLooping();

    /**
     * 判断是否正在播放。
     *
     * @return 播放器是否正在播放音乐
     */
    boolean isPlaying();

    /**
     * 获取当前音频文件的持续时间（单位：毫秒）。
     *
     * @return 当前音频文件的持续时间（单位：毫秒），如果持续时间未知，则返回 -1。
     */
    int getDuration();

    /**
     * 获取音频文件的当前播放进度。
     *
     * @return 音频文件的当前播放进度
     */
    int getProgress();

    /**
     * 开始播放。
     * <p>
     * 该方法对 WakeLock 和 WifiLock 进行了处理，你的音乐播放器实现应该重写该方法以实现开始播放功能。
     */
    void start();

    /**
     * 暂停播放。
     * <p>
     * 该方法对 WakeLock 和 WifiLock 进行了处理，你的音乐播放器实现应该重写该方法以实现暂停播放功能。
     */
    void pause();

    /**
     * 停止播放。
     * <p>
     * 该方法对 WakeLock 和 WifiLock 进行了处理，你的音乐播放器实现应该重写该方法以实现停止播放功能。
     */
    void stop();

    /**
     * 调整播放器的播放位置。
     *
     * @param pos 要调整到的播放位置。
     */
    void seekTo(int pos);

    /**
     * 设置音量为当前音量的百分比，范围：0.0 ~ 1.0
     *
     * @param leftVolume  左声道的音量百分比。
     * @param rightVolume 右声道的音量百分比。
     */
    void setVolume(float leftVolume, float rightVolume);

    /**
     * 临时降低音量。
     * <p>
     * 音量应该降低到不足以影响到其他应用的音频清晰度。
     */
    void quiet();

    /**
     * 从临时降低的音量中恢复原来的音量。
     */
    void dismissQuiet();

    /**
     * 释放音乐播放器。注意！一旦调用该方法，就不能再调用 MusicPlayer 对象的任何方法，否则会发生不可预见的错误。
     * <p>
     * 该方法对 WakeLock 和 WifiLock 进行了处理，你的音乐播放器实现应该重写该方法以释放占用的资源。
     */
    void release();

    /**
     * 当前音乐播放器是否已失效（对于新创建的对象，该方法默认返回 false）。
     * <p>
     * 当播放器发生错误，或者调用 {@link #release()} 方法后，该方法应该返回 true。如果该方法返回 true，则不
     * 应再调用除 {@link #release()} 方法以外的任何其他方法。
     *
     * @return 返回 true 表示当前音乐播放器已失效，此时不应再调用除 {@link #release()} 方法以外的任何其他方法
     */
    boolean isInvalid();

    /**
     * 获取音频会话 ID。如果失败，则返回 0。
     *
     * @return 音频会话 ID。如果失败，则返回 0。
     */
    int getAudioSessionId();

    /**
     * 设置一个 OnPreparedListener 监听器。
     */
    void setOnPreparedListener(OnPreparedListener listener);

    /**
     * 设置一个 OnCompletionListener 监听器。
     */
    void setOnCompletionListener(OnCompletionListener listener);

    /**
     * 设置一个 OnSeekCompleteListener 监听器。
     */
    void setOnSeekCompleteListener(OnSeekCompleteListener listener);

    /**
     * 设置一个 OnStalledListener 监听器。
     */
    void setOnStalledListener(OnStalledListener listener);

    /**
     * 设置一个 OnBufferingUpdateListener 监听器。
     */
    void setOnBufferingUpdateListener(OnBufferingUpdateListener listener);

    /**
     * 设置一个 OnErrorListener 监听器。
     */
    void setOnErrorListener(OnErrorListener listener);

    /**
     * 用于监听音乐播放器是否准备完毕。
     */
    interface OnPreparedListener {
        /**
         * 该方法会在音乐播放器准备完毕时被调用。
         *
         * @param mp 当前音乐播放器。
         */
        void onPrepared(MusicPlayer mp);
    }

    /**
     * 用于监听音乐播放器是否播放完毕。
     */
    interface OnCompletionListener {
        /**
         * 该方法会在音乐播放完毕时被调用。
         *
         * @param mp 当前音乐播放器。
         */
        void onCompletion(MusicPlayer mp);
    }

    /**
     * 用于监听音乐播放器的播放进度是的调整完毕。
     */
    interface OnSeekCompleteListener {
        /**
         * 该方法会在 seek 完成时被调用。
         *
         * @param mp 当前音乐播放器。
         */
        void onSeekComplete(MusicPlayer mp);
    }

    /**
     * 用于监听事件：进入缓冲区的数据变慢或停止并且缓冲区没有足够的数据继续播放。
     */
    interface OnStalledListener {
        /**
         * 该方法会在播放器的 stalled 状态改变时调用。
         *
         * @param stalled 如果缓冲区没有足够的数据继续播放，则该参数为 true，当缓冲区缓存了足够的数据可以继续
         *                播放时，该参数为 false。
         */
        void onStalled(boolean stalled);
    }

    /**
     * 用于监听音乐播放器的缓冲进度。
     */
    interface OnBufferingUpdateListener {
        /**
         * 该方法会在缓存进度更新时调用。
         *
         * @param mp        当前音乐播放器。
         * @param buffered  已缓存的进度
         * @param isPercent 已缓存的进度是否是百分比值
         */
        void onBufferingUpdate(MusicPlayer mp, int buffered, boolean isPercent);
    }

    /**
     * 用于监听器音乐播放的错误状态。
     */
    interface OnErrorListener {
        /**
         * 该方法会在错误发生时被调用。
         * <p>
         * 注意！当发生错误后，不允许再继续使用当前 MusicPlayer 对象，必须将其释放掉。如果需要继续播放，则
         * 需要创建一个新的 MusicPlayer 对象。
         *
         * @param mp        当前播放器。
         * @param errorCode 错误码。
         */
        void onError(MusicPlayer mp, int errorCode);
    }
}

package cc.sunshine.mixin.interfaces;

public interface IPlayerMoveC2SPacketMixin {

    void yawSetter(float yaw);

    void pitchSetter(float pitch);

    void groundSetter(boolean ground);
    void xSetter(double x);
    void ySetter(double y);
    void zSetter(double z);

}

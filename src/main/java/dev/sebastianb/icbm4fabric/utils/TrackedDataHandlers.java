package dev.sebastianb.icbm4fabric.utils;

import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.Vec3d;

public class TrackedDataHandlers {
    public static final TrackedDataHandler<Double> DOUBLE = new TrackedDataHandler<Double>() {
        @Override
        public void write(PacketByteBuf buf, Double value) {
            buf.writeDouble(value);
        }

        @Override
        public Double read(PacketByteBuf buf) {
            return buf.readDouble();
        }

        @Override
        public Double copy(Double value) {
            return value;
        }
    };

    public static final TrackedDataHandler<Vec3d> VEC_3D = new TrackedDataHandler<Vec3d>() {
        @Override
        public void write(PacketByteBuf buf, Vec3d value) {
            buf.writeDouble(value.getX());
            buf.writeDouble(value.getY());
            buf.writeDouble(value.getZ());
        }

        @Override
        public Vec3d read(PacketByteBuf buf) {
            return new Vec3d(buf.readDouble(), buf.readDouble(), buf.readDouble());
        }

        @Override
        public Vec3d copy(Vec3d value) {
            return value;
        }
    };
}

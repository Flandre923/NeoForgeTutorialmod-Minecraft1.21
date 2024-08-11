package com.example.examplemod.item.component.custom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.RecordBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

public record MyCustomData (int color, String name, int nutrition){

    public static Codec<MyCustomData> CODEC =
            RecordCodecBuilder.create(builder-> {
                return builder.group(Codec.INT.fieldOf("color").forGetter(MyCustomData::color),
                                Codec.STRING.fieldOf("name").forGetter(MyCustomData::name),
                                Codec.INT.fieldOf("nutrition").forGetter(MyCustomData::nutrition))
                        .apply(builder, MyCustomData::new);
            });

    public static StreamCodec<RegistryFriendlyByteBuf,MyCustomData> STREAM_CODEC = new StreamCodec<RegistryFriendlyByteBuf, MyCustomData>() {
        @Override
        public MyCustomData decode(RegistryFriendlyByteBuf friendlyByteBuf) {
            return  new MyCustomData(friendlyByteBuf.readInt(),friendlyByteBuf.readUtf(),friendlyByteBuf.readInt());
        }

        @Override
        public void encode(RegistryFriendlyByteBuf o, MyCustomData myCustomData) {
            o.writeInt(myCustomData.color());
            o.writeUtf(myCustomData.name());
            o.writeInt(myCustomData.nutrition());
        }
    };

}

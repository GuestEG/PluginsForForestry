package denoflionsx.PluginsforForestry.Core.CoreMod.ASM;

import denoflionsx.PluginsforForestry.Core.CoreMod.PfFCoreMod;
import denoflionsx.denLib.Lib.denLib;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;

public class ASMFluid implements IClassTransformer, Opcodes {

    public ASMFluid() {
    }

    @Override
    public byte[] transform(String string, String string1, byte[] bytes) {
        if (string.equals("net.minecraftforge.fluids.FluidContainerRegistry")) {
            PfFCoreMod.print("Injecting hook into " + string);
            return injectHook(bytes);
        }
        return bytes;
    }

    // This injects a method into the fluid container register that lets me unregister things.
    // This is so my server -> client syncing will work. I'm really hoping using it won't break other mods.
    private byte[] injectHook(byte[] bytes) {
        ClassNode cnode = denLib.ASMHelper.createClassNode(bytes);
        MethodVisitor mv;
        mv = cnode.visitMethod(ACC_PUBLIC + ACC_STATIC, "unregisterFluidContainer", "(Lnet/minecraftforge/fluids/FluidContainerRegistry$FluidContainerData;)Z", null, null);
        mv.visitCode();
        mv.visitVarInsn(ALOAD, 0);
        mv.visitFieldInsn(GETFIELD, "net/minecraftforge/fluids/FluidContainerRegistry$FluidContainerData", "filledContainer", "Lnet/minecraft/item/ItemStack;");
        mv.visitMethodInsn(INVOKESTATIC, "net/minecraftforge/fluids/FluidContainerRegistry", "isFilledContainer", "(Lnet/minecraft/item/ItemStack;)Z");
        Label l0 = new Label();
        mv.visitJumpInsn(IFNE, l0);
        mv.visitInsn(ICONST_0);
        mv.visitInsn(IRETURN);
        mv.visitLabel(l0);
        mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
        mv.visitFieldInsn(GETSTATIC, "net/minecraftforge/fluids/FluidContainerRegistry", "containerFluidMap", "Ljava/util/Map;");
        mv.visitInsn(ICONST_2);
        mv.visitTypeInsn(ANEWARRAY, "java/lang/Integer");
        mv.visitInsn(DUP);
        mv.visitInsn(ICONST_0);
        mv.visitVarInsn(ALOAD, 0);
        mv.visitFieldInsn(GETFIELD, "net/minecraftforge/fluids/FluidContainerRegistry$FluidContainerData", "filledContainer", "Lnet/minecraft/item/ItemStack;");
        mv.visitFieldInsn(GETFIELD, "net/minecraft/item/ItemStack", "field_77993_c", "I");
        mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
        mv.visitInsn(AASTORE);
        mv.visitInsn(DUP);
        mv.visitInsn(ICONST_1);
        mv.visitVarInsn(ALOAD, 0);
        mv.visitFieldInsn(GETFIELD, "net/minecraftforge/fluids/FluidContainerRegistry$FluidContainerData", "filledContainer", "Lnet/minecraft/item/ItemStack;");
        mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/item/ItemStack", "func_77960_j", "()I");
        mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
        mv.visitInsn(AASTORE);
        mv.visitMethodInsn(INVOKESTATIC, "java/util/Arrays", "asList", "([Ljava/lang/Object;)Ljava/util/List;");
        mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Map", "remove", "(Ljava/lang/Object;)Ljava/lang/Object;");
        mv.visitInsn(POP);
        mv.visitVarInsn(ALOAD, 0);
        mv.visitFieldInsn(GETFIELD, "net/minecraftforge/fluids/FluidContainerRegistry$FluidContainerData", "emptyContainer", "Lnet/minecraft/item/ItemStack;");
        Label l1 = new Label();
        mv.visitJumpInsn(IFNULL, l1);
        mv.visitVarInsn(ALOAD, 0);
        mv.visitFieldInsn(GETFIELD, "net/minecraftforge/fluids/FluidContainerRegistry$FluidContainerData", "emptyContainer", "Lnet/minecraft/item/ItemStack;");
        mv.visitFieldInsn(GETSTATIC, "net/minecraftforge/fluids/FluidContainerRegistry", "NULL_EMPTYCONTAINER", "Lnet/minecraft/item/ItemStack;");
        mv.visitJumpInsn(IF_ACMPEQ, l1);
        mv.visitFieldInsn(GETSTATIC, "net/minecraftforge/fluids/FluidContainerRegistry", "filledContainerMap", "Ljava/util/Map;");
        mv.visitInsn(ICONST_3);
        mv.visitTypeInsn(ANEWARRAY, "java/lang/Integer");
        mv.visitInsn(DUP);
        mv.visitInsn(ICONST_0);
        mv.visitVarInsn(ALOAD, 0);
        mv.visitFieldInsn(GETFIELD, "net/minecraftforge/fluids/FluidContainerRegistry$FluidContainerData", "emptyContainer", "Lnet/minecraft/item/ItemStack;");
        mv.visitFieldInsn(GETFIELD, "net/minecraft/item/ItemStack", "field_77993_c", "I");
        mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
        mv.visitInsn(AASTORE);
        mv.visitInsn(DUP);
        mv.visitInsn(ICONST_1);
        mv.visitVarInsn(ALOAD, 0);
        mv.visitFieldInsn(GETFIELD, "net/minecraftforge/fluids/FluidContainerRegistry$FluidContainerData", "emptyContainer", "Lnet/minecraft/item/ItemStack;");
        mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/item/ItemStack", "func_77960_j", "()I");
        mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
        mv.visitInsn(AASTORE);
        mv.visitInsn(DUP);
        mv.visitInsn(ICONST_2);
        mv.visitVarInsn(ALOAD, 0);
        mv.visitFieldInsn(GETFIELD, "net/minecraftforge/fluids/FluidContainerRegistry$FluidContainerData", "fluid", "Lnet/minecraftforge/fluids/FluidStack;");
        mv.visitFieldInsn(GETFIELD, "net/minecraftforge/fluids/FluidStack", "fluidID", "I");
        mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
        mv.visitInsn(AASTORE);
        mv.visitMethodInsn(INVOKESTATIC, "java/util/Arrays", "asList", "([Ljava/lang/Object;)Ljava/util/List;");
        mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Map", "remove", "(Ljava/lang/Object;)Ljava/lang/Object;");
        mv.visitInsn(POP);
        mv.visitFieldInsn(GETSTATIC, "net/minecraftforge/fluids/FluidContainerRegistry", "emptyContainers", "Ljava/util/Set;");
        mv.visitInsn(ICONST_2);
        mv.visitTypeInsn(ANEWARRAY, "java/lang/Integer");
        mv.visitInsn(DUP);
        mv.visitInsn(ICONST_0);
        mv.visitVarInsn(ALOAD, 0);
        mv.visitFieldInsn(GETFIELD, "net/minecraftforge/fluids/FluidContainerRegistry$FluidContainerData", "emptyContainer", "Lnet/minecraft/item/ItemStack;");
        mv.visitFieldInsn(GETFIELD, "net/minecraft/item/ItemStack", "field_77993_c", "I");
        mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
        mv.visitInsn(AASTORE);
        mv.visitInsn(DUP);
        mv.visitInsn(ICONST_1);
        mv.visitVarInsn(ALOAD, 0);
        mv.visitFieldInsn(GETFIELD, "net/minecraftforge/fluids/FluidContainerRegistry$FluidContainerData", "emptyContainer", "Lnet/minecraft/item/ItemStack;");
        mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/item/ItemStack", "func_77960_j", "()I");
        mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
        mv.visitInsn(AASTORE);
        mv.visitMethodInsn(INVOKESTATIC, "java/util/Arrays", "asList", "([Ljava/lang/Object;)Ljava/util/List;");
        mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Set", "remove", "(Ljava/lang/Object;)Z");
        mv.visitInsn(POP);
        mv.visitLabel(l1);
        mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
        mv.visitInsn(ICONST_1);
        mv.visitInsn(IRETURN);
        mv.visitMaxs(5, 1);
        mv.visitEnd();
        return denLib.ASMHelper.createBytes(cnode, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
    }
}

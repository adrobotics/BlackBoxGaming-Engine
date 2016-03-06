package com.blackboxgaming.engine.factories;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder.VertexInfo;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;

/**
 *
 * @author Adrian
 */
public class ModelFactory {

    private static final ModelBuilder modelBuilder = new ModelBuilder();

    public static Model getCubeModel(float size) {
        return modelBuilder.createBox(size, size, size, new Material(ColorAttribute.createDiffuse(Color.BLUE)), Usage.Position | Usage.Normal);
    }

    public static Model getSphereModel(float size) {
        return modelBuilder.createSphere(size, size, size, 10, 10, new Material(ColorAttribute.createDiffuse(Color.GREEN)), Usage.Position | Usage.Normal);
    }

    public static Model getSphereModel(float size, int divisions) {
        return modelBuilder.createSphere(size, size, size, divisions, divisions, new Material(ColorAttribute.createDiffuse(Color.GREEN)), Usage.Position | Usage.Normal);
    }

    public static Model getConeModel(float size) {
        return modelBuilder.createCone(size, 2 * size, size, 10, new Material(ColorAttribute.createDiffuse(Color.YELLOW)), Usage.Position | Usage.Normal);
    }

    public static Model getCapsuleModel(float size) {
        return modelBuilder.createCapsule(0.5f * size, 2 * size, 10, new Material(ColorAttribute.createDiffuse(Color.CYAN)), Usage.Position | Usage.Normal);
    }

    public static Model getCylinderModel(float size) {
        return modelBuilder.createCylinder(size, 2f * size, size, 10, new Material(ColorAttribute.createDiffuse(Color.MAGENTA)), Usage.Position | Usage.Normal);
    }

    public static Model getCylinderModel(float radius, float length, int divisions) {
        return modelBuilder.createCylinder(radius * 2f, length, radius * 2f, divisions, new Material(ColorAttribute.createDiffuse(Color.MAGENTA)), Usage.Position | Usage.Normal);
    }

    public static Model getTileModel(float size) {
        return modelBuilder.createBox(size, size / 10f, size, new Material(ColorAttribute.createDiffuse(Color.RED)), Usage.Position | Usage.Normal);
    }

    public static Model getGridModel(int divisions) {
        return modelBuilder.createLineGrid(divisions, divisions, 1, 1, new Material(ColorAttribute.createDiffuse(Color.LIGHT_GRAY)), Usage.Position);
    }

    public static Model getArrowModel(Vector3 from, Vector3 to) {
        return modelBuilder.createArrow(from, to, new Material(ColorAttribute.createDiffuse(Color.RED)), Usage.Position | Usage.Normal);
    }

    public static Model getBoxModel(float width, float height, float depth) {
        return modelBuilder.createBox(width, height, depth, new Material(ColorAttribute.createDiffuse(Color.OLIVE)), Usage.Position | Usage.Normal);
    }

    public static Model getXYZCoordinates(float size) {
        return modelBuilder.createXYZCoordinates(size, new Material(ColorAttribute.createDiffuse(Color.OLIVE)), Usage.Position | Usage.Normal);
    }

    public static Model getConcaveModel() {
        Model m = modelBuilder.createBox(3, 3, 3, new Material(ColorAttribute.createDiffuse(Color.OLIVE)), Usage.Position | Usage.Normal);
        Model m2 = modelBuilder.createBox(2, 20, 2, new Material(ColorAttribute.createDiffuse(Color.OLIVE)), Usage.Position | Usage.Normal);

        modelBuilder.begin();
        MeshPartBuilder meshPartBuilder = modelBuilder.part("part1", GL20.GL_TRIANGLES, Usage.Position | Usage.Normal, new Material(ColorAttribute.createDiffuse(Color.OLIVE)));
        meshPartBuilder.setColor(Color.RED);
        VertexInfo v1 = new VertexInfo().setPos(0, 0, 0).setNor(0, 0, 1).setCol(null).setUV(0.5f, 0.0f);
        VertexInfo v2 = new VertexInfo().setPos(3, 0, 0).setNor(0, 0, 1).setCol(null).setUV(0.0f, 0.0f);
        VertexInfo v3 = new VertexInfo().setPos(3, 3, 0).setNor(0, 0, 1).setCol(null).setUV(0.0f, 0.5f);
        VertexInfo v4 = new VertexInfo().setPos(0, 3, 0).setNor(0, 0, 1).setCol(null).setUV(0.5f, 0.5f);
        meshPartBuilder.rect(v1, v2, v3, v4);
        modelBuilder.part(meshPartBuilder.getMeshPart(), new Material(ColorAttribute.createDiffuse(Color.OLIVE)));
        modelBuilder.part(m2.meshParts.first(), new Material(ColorAttribute.createDiffuse(Color.OLIVE)));
        return modelBuilder.end();
    }
}

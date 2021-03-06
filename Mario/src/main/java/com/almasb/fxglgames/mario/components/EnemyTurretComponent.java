package com.almasb.fxglgames.mario.components;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.view.ChildViewComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.Texture;
import com.almasb.fxgl.time.LocalTimer;
import com.almasb.fxgl.time.TimerAction;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

/**
 * @author Almas Baimagambetov (almaslvl@gmail.com)
 */
public class EnemyTurretComponent extends ChildViewComponent {

    private LocalTimer shootTimer;

    private Entity player;

    private Texture turretHeadTexture;

    private Rotate rotate = new Rotate(0, 34.5, 16.5);

    public EnemyTurretComponent() {
        super(0, 0, false);

        turretHeadTexture = FXGL.texture("enemies/turret/sprite_1.png");
        turretHeadTexture.getTransforms().add(rotate);

        getViewRoot().getChildren().add(turretHeadTexture);
    }

    @Override
    public void onAdded() {
        super.onAdded();

        shootTimer = FXGL.newLocalTimer();
        shootTimer.capture();
    }

    @Override
    public void onUpdate(double tpf) {
        if (player == null) {
            player = FXGL.geto("player");
        }

        rotate.setAngle(new Vec2(player.getPosition().subtract(entity.getPosition())).angle());

        if (shootTimer.elapsed(Duration.seconds(0.7))) {
            if (player != null) {
                FXGL.spawn("enemyTurretProjectile",
                        new SpawnData(entity.getPosition().add(rotate.getPivotX(), rotate.getPivotY()))
                                .put("direction", player.getPosition().subtract(entity.getPosition()))
                );
            }
            shootTimer.capture();
        }
    }
}

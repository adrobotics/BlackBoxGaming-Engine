package com.blackboxgaming.engine;

import com.blackboxgaming.engine.components.Damage;
import com.blackboxgaming.engine.components.Health;
import com.blackboxgaming.engine.components.Sound;
import java.util.HashSet;
import java.util.Set;
import org.junit.Test;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

/**
 *
 * @author adrian.popa
 */
public class EntityTest {

    @Test
    public void testUniqueIds() {
        int max = 10_000;
        Set<Entity> ids = new HashSet(max);
        for (int i = 0; i < max; i++) {
            ids.add(new Entity());
        }
        assertEquals(max, ids.size());
    }

    @Test
    public void testAddAndHasComponent() {
        Entity e = new Entity();
        assertFalse(e.has(Health.class));
        e.add(new Health(100));
        assertTrue(e.has(Health.class));
    }

    @Test
    public void testAddComponentTwice() {
        Entity e = new Entity();
        e.add(new Health(100));
        e.add(new Health(99));
        assertEquals(100, e.get(Health.class).maxHealth, 0);
    }

    @Test
    public void testGetComponent() {
        Entity e = new Entity();
        Health health = new Health(100);
        e.add(health);
        assertEquals(health, e.get(Health.class));
    }

    @Test
    public void testRemoveComponent() {
        Entity e = new Entity();
        Health health = new Health(100);
        e.add(health);
        assertEquals(health, e.remove(Health.class));
        assertNull(e.remove(Health.class));
        assertFalse(e.has(Health.class));
    }

    @Test
    public void testDispose() {
        Entity e = new Entity();
        Sound sound = mock(Sound.class);
        e.add(sound);
        e.dispose();
        verify(sound).dispose();
    }

    @Test
    public void testPrintsIdAndComponents() {
        Entity e = new Entity();
        assertTrue(e.toString().contains("" + e.id));
        Health health = new Health(100);
        e.add(health);
        assertTrue(e.toString().contains("" + e.id));
        assertTrue(e.toString().contains(health.toString()));
        Damage damage = new Damage(50);
        e.add(damage);
        assertTrue(e.toString().contains("" + e.id));
        assertTrue(e.toString().contains(health.toString()));
        assertTrue(e.toString().contains(damage.toString()));
    }

}

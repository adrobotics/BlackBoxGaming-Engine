package com.blackboxgaming.engine.systems.conway;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Adrian
 */
public class RuleTest {
    
    @Test
    public void testWillSurvive() {
        Rule rule = new Rule(1, 1, 0, 0);
        assertFalse(rule.willSurvive(0));
        assertTrue(rule.willSurvive(1));
        assertFalse(rule.willSurvive(2));
        
        rule = new Rule(1, 2, 0, 0);
        assertFalse(rule.willSurvive(0));
        assertTrue(rule.willSurvive(1));
        assertTrue(rule.willSurvive(2));
        assertFalse(rule.willSurvive(3));
    }
    
    @Test
    public void testWillGiveBirth() {
        Rule rule = new Rule(0, 0, 1, 1);
        assertFalse(rule.willGiveBirth(0));
        assertTrue(rule.willGiveBirth(1));
        assertFalse(rule.willGiveBirth(2));
        
        rule = new Rule(0, 0, 1, 2);
        assertFalse(rule.willGiveBirth(0));
        assertTrue(rule.willGiveBirth(1));
        assertTrue(rule.willGiveBirth(2));
        assertFalse(rule.willGiveBirth(3));
    }
    
}

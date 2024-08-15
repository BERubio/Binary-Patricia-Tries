package bpt;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * A jUnit test suite for {@link BinaryPatriciaTrie}.
 *
 * @author --- BRANDON RUBIO ----.
 */
public class StudentTests {


    @Test public void testEmptyTrie() {
        BinaryPatriciaTrie trie = new BinaryPatriciaTrie();

        assertTrue("Trie should be empty",trie.isEmpty());
        assertEquals("Trie size should be 0", 0, trie.getSize());

        assertFalse("No string inserted so search should fail", trie.search("0101"));

    }

    @Test public void testFewInsertionsWithSearch() {
        BinaryPatriciaTrie trie = new BinaryPatriciaTrie();

        assertTrue("String should be inserted successfully",trie.insert("00000"));
        assertTrue("String should be inserted successfully",trie.insert("00011"));
        assertTrue("Trie should have size of 2 but was " + trie.getSize(), trie.getSize() == 2);
        assertFalse("Search should fail as string does not exist",trie.search("000"));

    }


    
    @Test public void testFewInsertionsWithDeletion() {
        BinaryPatriciaTrie trie = new BinaryPatriciaTrie();
       
        System.out.println(trie.getSize());
        
        trie.insert("000");
        
        trie.printTrie();
        System.out.println(trie.getSize());
        
        trie.insert("001");
        
        trie.printTrie();
        System.out.println(trie.getSize());
        
        trie.insert("011");
        
        trie.printTrie();
        System.out.println(trie.getSize());
        
        trie.insert("1001");
        
        trie.printTrie();
        System.out.println(trie.getSize());
        
        trie.insert("1");
        
        trie.printTrie();
        System.out.println(trie.getSize());
        
        trie.printTrie();
        
        assertFalse("After inserting five strings, the trie should not be considered empty!", trie.isEmpty());
        assertEquals("After inserting five strings, the trie should report five strings stored.", 5, trie.getSize());

        trie.delete("0"); // Failed deletion; should affect exactly nothing.
        
        trie.print2();
        
        assertEquals("After inserting five strings and requesting the deletion of one not in the trie, the trie " +
                "should report five strings stored.", 5, trie.getSize());
        assertTrue("After inserting five strings and requesting the deletion of one not in the trie, the trie had some junk in it!",
                trie.isJunkFree());

        trie.delete("011"); // Successful deletion
        trie.print2();
        assertEquals("After inserting five strings and deleting one of them, the trie should report 4 strings.", 4, trie.getSize());
        assertTrue("After inserting five strings and deleting one of them, the trie had some junk in it!",
                trie.isJunkFree());
    }
    
    
    //MY TESTS------------------------>
    @Test
    public void testSearch() {
    	BinaryPatriciaTrie trie = new BinaryPatriciaTrie();
        
        
        trie.insert("000");
        assertFalse("Search should fail, key isn't in the trie", trie.search("00"));
    }
    
    @Test
    public void testSearch2() {
    	BinaryPatriciaTrie trie = new BinaryPatriciaTrie();
        
        
        trie.insert("000");
        trie.insert("111");
        trie.insert("110");
        trie.printTrie();
        trie.print2();
        assertTrue("Search should succeed, key is in the trie", trie.search("111"));
    }
    
    @Test 
    public void testInsert2() {
    	BinaryPatriciaTrie trie = new BinaryPatriciaTrie();
    	
    	trie.insert("0");
    	trie.insert("111");
    	trie.insert("110");
    	trie.insert("000");
    	trie.printTrie();
    	trie.print2();
    	
    	System.out.println(trie.search("000"));
    }
    
    @Test 
    public void testSimple2() {
    	BinaryPatriciaTrie trie = new BinaryPatriciaTrie();
    	
    	trie.insert("00000");
    	trie.printTrie();
    	trie.insert("00011");
    	trie.printTrie();
    	trie.insert("000");
    	
    	trie.printTrie();
    	trie.print2();
    	
    	assertTrue(trie.search("000"));
    }
    
    @Test
    public void testSimple4() {
    	BinaryPatriciaTrie trie = new BinaryPatriciaTrie();
    	
    	trie.insert("100");
    	trie.printTrie();
    	trie.insert("101");
    	trie.printTrie();
    	trie.print2();
    	trie.delete("100");
    	trie.printTrie();
    	trie.print2();
    	
    	//assertFalse(trie.search("100"));
    	assertEquals(1, trie.getSize());
    }
    
    @Test
    public void testSimple5() {
    	BinaryPatriciaTrie trie = new BinaryPatriciaTrie();
    	trie.insert("100");
    	trie.insert("101");
    	
    	trie.printTrie();
    	trie.print2();
    	
    	trie.insert("0");
    	
    	trie.printTrie();
    	trie.print2();
    	
    	trie.insert("10111");
    	
    	trie.printTrie();
    	trie.print2();
    	
    	trie.insert("1011");
    	
    	trie.printTrie();
    	trie.print2();
    	
    	trie.insert("1010");
    	
    	trie.printTrie();
    	trie.print2();
    	//Expected behavior up to this point
    	
    	//problem arose after the insertion of 1
    	trie.insert("1");
    	
    	trie.printTrie();
    	trie.print2();
    	
    	assertTrue(trie.search("1011"));
    }
    
    @Test
    public void testInsert() {
    	BinaryPatriciaTrie trie = new BinaryPatriciaTrie();
    	trie.insert("0101");
    	trie.printTrie();
    	trie.insert("1101");
    	trie.printTrie();
    	
    	trie.print2();
    	//assertEquals(2, trie.getSize());
    	assertFalse(trie.search("0"));
    }
    
    @Test
    public void testGetSize() {
    	BinaryPatriciaTrie trie = new BinaryPatriciaTrie();
    	trie.insert("0101");
    	trie.insert("0100");
    	trie.insert("1100");
    	
    	trie.printTrie();
    	trie.print2();
    	
    	trie.delete("0101");
    	trie.delete("0100");
    	trie.printTrie();
    	trie.print2();
    	assertEquals(1, trie.getSize());
    	trie.printTrie();
    }
    
    
    @Test
    public void testDelete() {
    	BinaryPatriciaTrie trie = new BinaryPatriciaTrie();
    	trie.insert("0");
    	trie.insert("00");
    	trie.insert("01");
    	trie.printTrie();
    	trie.print2();
    	
    	trie.delete("0");
    	trie.printTrie();
    	trie.print2();
    	
    	assertFalse(trie.search("0"));
    }
    
    @Test
    public void testGetLongest() {
    	BinaryPatriciaTrie trie = new BinaryPatriciaTrie();
    	trie.insert("11");
    	trie.insert("0101");
    	assertEquals("0101",trie.getLongest());
    }
    
    @Test
	public void testSimpleInOrderTraversal() {
		BinaryPatriciaTrie trie = new BinaryPatriciaTrie();

		trie.insert("000001");
		trie.insert("010010");
		trie.insert("010111");
		trie.insert("011011");
		trie.insert("011101");
		trie.insert("011111");
		trie.insert("100100");
		
		Iterator<String> iter =  trie.inorderTraversal();
		while (iter.hasNext()) {
			System.out.println(iter.next());
		}
		
		
	}
    
    
}
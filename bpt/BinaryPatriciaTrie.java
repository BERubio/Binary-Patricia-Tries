package bpt;

import bpt.UnimplementedMethodException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * <p>
 * {@code BinaryPatriciaTrie} is a Patricia Trie over the binary alphabet &#123;
 * 0, 1 &#125;. By restricting themselves to this small but terrifically useful
 * alphabet, Binary Patricia Tries combine all the positive aspects of Patricia
 * Tries while shedding the storage cost typically associated with tries that
 * deal with huge alphabets.
 * </p>
 *
 * @author BRANDON RUBIO
 */
public class BinaryPatriciaTrie {

	/*
	 * We are giving you this class as an example of what your inner node might look
	 * like. If you would prefer to use a size-2 array or hold other things in your
	 * nodes, please feel free to do so. We can *guarantee* that a *correct*
	 * implementation exists with *exactly* this data stored in the nodes.
	 */
	private static class TrieNode {
		private TrieNode left, right;
		private String str;

		private boolean isKey;

		// Default constructor for your inner nodes.
		TrieNode() {
			this("", false);
		}

		// Non-default constructor.
		TrieNode(String str, boolean isKey) {
			this.str = str;
			this.isKey = isKey;
		}
	}

	private TrieNode root;
	private int size;

	/**
	 * Simple constructor that will initialize the internals of {@code this}.
	 */
	public BinaryPatriciaTrie() {
		this.root = new TrieNode();
		this.size = 0;
	}

	public void printTrie() {
		System.out.println("TRIE----->");
		System.out.println("Root: " + this.root.str + ", Key: " + this.root.isKey);
		System.out.println("Root-Left Subtree: ");
		print(this.root.left);
		System.out.println("Root-Right Subtree: ");
		print(this.root.right);
	}

	private void print(TrieNode node) {
		if (node == null) {
			System.out.println("NULL");
			return;
		}
		System.out.println("Node: " + node.str + ", Key: " + node.isKey);
		System.out.println("Left Subtree of " + node.str + ":");
		print(node.left);
		System.out.println("Right Subtree of " + node.str + ":");
		print(node.right);
	}
	
	public void print2() {
		System.out.println("KEYS----->");
		System.out.println("Root: " + this.root.str + ", Key: " + this.root.isKey);
		String keys = "";
		print2Help(root.left, keys);
		print2Help(root.right, keys);
	}

	private void print2Help(TrieNode node, String keys) {
		if (node == null) {
			return;
		}
		keys += node.str;
		if(node.isKey) {
			System.out.println("Keys: " +keys);
		}
		print2Help(node.left, keys);
		print2Help(node.right, keys);
	}

	/**
	 * Searches the trie for a given key.
	 *
	 * @param key The input {@link String} key.
	 * @return {@code true} if and only if key is in the trie, {@code false}
	 *         otherwise.
	 */
	public boolean search(String key) {
		TrieNode current = root;
		int index = 0;
		
		while (index < key.length()) {
			System.out.println("DEBUG 7---> Key being searched for: " + key + ", Index: "+ index);
			if (key.charAt(index) == '0') {

				if (current.left == null) {
					return false;
				}

				current = current.left;
				// CASE 1: Node string is the same as the key
				System.out.println("DEBUG 4: LEFT Current String: " + current.str +", Key: "+ key + ", current.isKey: "+ current.isKey);
				if (current.str.equals(key)) {
					System.out.println("DEBUG 4: LEFT Strings MATCH!, Current String: " + current.str +", Key: "+ key + ", current.isKey: "+ current.isKey);
					if (current.isKey) {
						return true;
					} else {
						return false;
					}
				}
				
				if(current.str.startsWith(key)) {
					return false;
				}
				
				// CASE 2: Node string is a prefix of the key
				if (key.startsWith(current.str)) {
					// set index to index where common prefix ends
					index = indexOfDifference(key, current.str);
					// consume all prefix characters
					key = key.substring(index, key.length());
					index = 0;
				}
			} else {
				// key.charAt(index) == '1'
				if (current.right == null) {
					return false;
				}

				current = current.right;
				System.out.println("DEBUG 4: RIGHT Current String: " + current.str +", Key: "+ key + ", current.isKey: "+ current.isKey);
				if (current.str.equals(key)) {
					System.out.println("DEBUG 4: RIGHT Strings MATCH!, Current String: " + current.str +", Key: "+ key + ", current.isKey: "+ current.isKey);
					if (current.isKey) {
						return true;
					} else {
						return false;
					}
				}
				
				if(current.str.startsWith(key)) {
					return false;
				}

				// CASE 2: Node string is a prefix of the key
				if (key.startsWith(current.str)) {
					// set index to index where common prefix ends
					index = indexOfDifference(key, current.str);
					
					// pull substring out after common prefix
					key = key.substring(index, key.length());
					index = 0;
				}
			}
			//index++;
		}
		return false;
	}

	/**
	 * Inserts key into the trie.
	 *
	 * @param key The input {@link String} key.
	 * @return {@code true} if and only if the key was not already in the trie,
	 *         {@code false} otherwise.
	 */
	public boolean insert(String key) {

		if (search(key)) {
			// key already in the trie
			System.out.println("Was found: "+key);
			return false;
		}
		// key is not in the trie

		size++;
		
		boolean temp = insertHelper(key);
		System.out.println("DEBUG 5 --> Search After Insert: " + search(key));
		return temp;
	}

	private boolean insertHelper(String key) {
		System.out.println("INSERT HELPER Key Received: "+ key);
		TrieNode current = root;
		int index = 0;
		int oldLength = key.length();
		String oldKey = key;
		
		while (index < oldLength) {
			System.out.println("Top of While Loop -> KEY: "+key+", Index: " +index);
			if (key.charAt(index) == '0') {
				System.out.println("DEBUG 3---> Went Left!, Key: " + key);
				if (current.left == null) {
					// Null node found, allocate and finish
					current.left = new TrieNode(key, true);
					// size++;
					return true;
				}

				current = current.left;

				if (key.equals(current.str)) {
					if(current.isKey) {
						return false;
					}else {
						current.isKey = true;
					}
					return true;
					//index = key.length() - 1;
				} else if (key.startsWith(current.str)) {
					// current node is a prefix of the key
					index = indexOfDifference(key, current.str);
					// consume all prefix characters
					key = key.substring(index, key.length());
					index = 0;
					System.out.println("DEBUG --> Key After Consume: " + key+ ", Index: " + index);
				}/* else if (current.str.startsWith(key)) {
					index = indexOfDifference(current.str, key);
					
					String newCurrString = current.str.substring(0, index);
					key = current.str.substring(index, current.str.length());
					
					current.str = newCurrString;
					current.isKey = true;
					
					index = 0;
				}*/ else {
					// Splitting a Node
					String oldStr = current.str;
					System.out.println("Current Node: " + current.str);

					String commonPrefix = findCommonPrefix(key, current.str);

					System.out.println("Common Prefix String: " + commonPrefix);

					int split = indexOfDifference(commonPrefix, current.str);

					// String newCurrString = current.str.substring(0, split);
					String newCurrString = commonPrefix;

					String splitNodeString1 = current.str.substring(split, current.str.length());
					System.out.println("Split1: " + splitNodeString1);
					String splitNodeString2 = key.substring(split, key.length());
					System.out.println("Split2: " + splitNodeString2);

					
					current.str = newCurrString;
					
					boolean boolKey = current.isKey;
					
					current.isKey = false;
					// size--;
					
					if (splitNodeString1.charAt(0) == '0') {
						TrieNode oldLeft = current.left;
						TrieNode oldRight = current.right;

						current.right = null;

						current.left = new TrieNode(splitNodeString1, boolKey);
						
						current.isKey = false;

						current.left.left = oldLeft;

						current = current.left;

						current.right = oldRight;

						return insertHelper(oldKey);

					} else {

						TrieNode oldLeft = current.left;
						TrieNode oldRight = current.right;

						current.left = null;

						current.right = new TrieNode(splitNodeString1, boolKey);
						
						current.isKey = false;

						current.right.right = oldRight;

						current = current.right;

						current.left = oldLeft;

						return insertHelper(oldKey);
						
					}
				}
			} else {
				System.out.println("DEBUG 3---> Went Right!, Key: " + key);
				// key.charAt(index) == '1'
				if (current.right == null) {
					// Null node found, allocate and finish
					System.out.println("DEBUG 2---> KEY: " + key);
					current.right = new TrieNode(key, true);
					// size++;
					return true;
				}

				current = current.right;

				if (key.equals(current.str)) {
					current.isKey = true;
					return true;
					//index = key.length() - 1;
				} else if (key.startsWith(current.str)) {
					// current node is a prefix of the key
					index = indexOfDifference(key, current.str);
					// consume all prefix characters
					key = key.substring(index, key.length());
					index = 0;
				}/* else if (current.str.startsWith(key)) {
					//Create new Parent
					
					index = indexOfDifference(current.str, key);
						
					String newCurrString = current.str.substring(0, index);
					key = current.str.substring(index, current.str.length());
					
					current.str = newCurrString;
					current.isKey = true;
					
					index = 0;
					
				}*/ else {
					// Splitting a Node
					String oldStr = current.str;
					System.out.println("Splitting Node on Right Subtree!");
					String commonPrefix = findCommonPrefix(key, current.str);
					System.out.println("Common Prefix String: " + commonPrefix);
					int split = indexOfDifference(commonPrefix, current.str);

					String newCurrString = commonPrefix;

					String splitNodeString1 = current.str.substring(split, current.str.length());
					System.out.println("Split1: " + splitNodeString1);
					String splitNodeString2 = key.substring(split, key.length());
					System.out.println("Split2: " + splitNodeString2);

					current.str = newCurrString;
					boolean boolKey = current.isKey;
					current.isKey = false;
					// size--;

					if (splitNodeString1.charAt(0) == '0') {

						TrieNode oldLeft = current.left;
						TrieNode oldRight = current.right;

						current.right = null;

						current.left = new TrieNode(splitNodeString1, boolKey);

						current.isKey = false;

						current.left.left = oldLeft;

						current = current.left;

						current.right = oldRight;

						return insertHelper(oldKey);
					} else {

						TrieNode oldLeft = current.left;
						TrieNode oldRight = current.right;

						current.left = null;

						current.right = new TrieNode(splitNodeString1, boolKey);
						
						current.isKey = false;

						current.right.right = oldRight;

						current = current.right;

						current.left = oldLeft;

						return insertHelper(oldKey);
					}
				}
			}
		}
		return false;
	}

	private String findCommonPrefix(String str1, String str2) {
		int i = 0;
		while (i < str1.length() && i < str2.length() && str1.charAt(i) == str2.charAt(i)) {
			i++;
		}
		return str1.substring(0, i);
	}

	private int indexOfDifference(String str1, String str2) {
		if (str1 == str2) {
			return -1;
		}
		if (str1 == null || str2 == null) {
			return 0;
		}
		int i;
		for (i = 0; i < str1.length() && i < str2.length(); i++) {
			if (str1.charAt(i) != str2.charAt(i)) {
				break;
			}
		}
		if (i < str1.length() || i < str2.length()) {
			return i;
		}
		return -1;
	}

	/**
	 * Deletes key from the trie.
	 *
	 * @param key The {@link String} key to be deleted.
	 * @return {@code true} if and only if key was contained by the trie before we
	 *         attempted deletion, {@code false} otherwise.
	 */
	public boolean delete(String key) {
		System.out.println("Attempting to delete Key: " + key);
		if (!search(key)) {
			System.out.println("DEBUG 8---> Key to be deleted Not Found: " + key);
			
			return false;
		}

		TrieNode current = root;
		TrieNode parent = null;

		int index = 0;

		while (index < key.length()) {
			System.out.println("DEBUG 6---> Key to be deleted: " + key + ", Index: "+ index);
			parent = current;
			if (key.charAt(index) == '0') {
				current = current.left;

				if (current.str.equals(key)) {
					if (current.left == null && current.right == null) {
						parent.left = null;
						//call helper to clean
						clean(parent);
						current = null;
						size--;
						
						if(root.str != "" && root.left == null && root.right == null) {
							String temp = root.str;
							root.str = "";
							root.isKey = false;
							size--;
							insert(temp);
						}
						
						return true;
					} else if (current.left == null || current.right == null) {
						current.isKey = false;

						if (current.left != null) {
							current.str += current.left.str;
							current.left = null;
							current.isKey = true;
						} else {
							current.str = current.right.str;
							current.right = null;
							current.isKey = true;
						}
					} else {
						// current node has two children
						current.isKey = false;
						size--;
						return true;
					}
				} else if (key.startsWith(current.str)) {
					// current node is a prefix of the key
					index = indexOfDifference(key, current.str);
					// consume all prefix characters
					key = key.substring(index, key.length());
					System.out.println("DEBUG 9: LEFT, Reduced key to: " + key);
					index = 0;
				}
			} else {
				current = current.right;

				if (current.str.equals(key)) {
					if (current.left == null && current.right == null) {
						parent.right = null;
						clean(parent);
						current = null;
						size--;
						
						if(root.str != "" && root.left == null && root.right == null) {
							String temp = root.str;
							root.str = "";
							root.isKey = false;
							size--;
							insert(temp);
						}
						
						return true;
					} else if (current.left == null || current.right == null) {
						current.isKey = false;

						if (current.left != null) {
							current.str += current.left.str;
							current.left = null;
							current.isKey = true;
						} else {
							current.str = current.right.str;
							current.right = null;
							current.isKey = true;
						}
					} else {
						current.isKey = false;
						size--;
						return true;
					}
				} else if (key.startsWith(current.str)) {
					// current node is a prefix of the key
					index = indexOfDifference(key, current.str);
					// consume all prefix characters
					key = key.substring(index, key.length());
					System.out.println("DEBUG 9: RIGHT, Reduced key to: " + key);
					index = 0;
				}
			}
			//index++;
		}
		current.isKey = false;
		size--;
		return true;
	}

	// private function for clearing Junk in trie
	private void clean(TrieNode node) {
		if(!node.isKey && (node.left == null || node.right == null)) {
			if(node.left == null && node.right == null) {
				return;
			}else {
				if(node.left == null) {
					if(node.right == null) {
						return;
					}
					
					node.str += node.right.str;
					node.isKey = node.right.isKey;
					if(node.right.right != null) {
						node.right = node.right.right;
					}else {
						node.right = null;
						return;
					}
					
					if(node.right.left != null) {
						node.left = node.right.left;
					}else {
						node.left = null;
						//size--;
					}
					
				}else {
					node.str += node.left.str;
					node.isKey = node.left.isKey;
					node.right = node.left.right;
					node.left = node.left.left;
				}
			}
		}
			
		
	}

	/**
	 * Queries the trie for emptiness.
	 *
	 * @return {@code true} if and only if {@link #getSize()} == 0, {@code false}
	 *         otherwise.
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Returns the number of keys in the tree.
	 *
	 * @return The number of keys in the tree.
	 */
	public int getSize() {
		return size;
	}

	/**
	 * <p>
	 * Performs an <i>inorder (symmetric) traversal</i> of the Binary Patricia Trie.
	 * Remember from lecture that inorder traversal in tries is NOT sorted
	 * traversal, unless all the stored keys have the same length. This is of course
	 * not required by your implementation, so you should make sure that in your
	 * tests you are not expecting this method to return keys in lexicographic
	 * order. We put this method in the interface because it helps us test your
	 * submission thoroughly and it helps you debug your code!
	 * </p>
	 *
	 * <p>
	 * We <b>neither require nor test </b> whether the {@link Iterator} returned by
	 * this method is fail-safe or fail-fast. This means that you do <b>not</b> need
	 * to test for thrown {@link java.util.ConcurrentModificationException}s and we
	 * do <b>not</b> test your code for the possible occurrence of concurrent
	 * modifications.
	 * </p>
	 *
	 * <p>
	 * We also assume that the {@link Iterator} is <em>immutable</em>, i,e we do
	 * <b>not</b> test for the behavior of {@link Iterator#remove()}. You can handle
	 * it any way you want for your own application, yet <b>we</b> will <b>not</b>
	 * test for it.
	 * </p>
	 *
	 * @return An {@link Iterator} over the {@link String} keys stored in the trie,
	 *         exposing the elements in <i>symmetric order</i>.
	 */
	public Iterator<String> inorderTraversal() {
		return new TrieIterator();
	}

	public class TrieIterator implements Iterator<String>{
		private ArrayList<String> nodes;
		private int index;
		
		public TrieIterator() {
			nodes = new ArrayList<String>();
			String keys = "";
			addToList(root, keys);
			index = 0;
		}
		
		public void addToList(TrieNode node, String keys) {
			if(node == null) {
				return;
			}
			keys += node.str;
			
			addToList(node.left, keys);
			
			if(node.isKey) {
				nodes.add(keys);
			}
			
			addToList(node.right, keys);
		}

		@Override
		public boolean hasNext() {
			if(index < nodes.size()) {
				return true;
			}
			return false;
		}

		@Override
		public String next() {
			if(hasNext()) {
				index++;
				return nodes.get(index-1);
			}else {
				throw new NoSuchElementException();
			}
		}
		
		
	}
	
	/*private void inOrderTraversal(TrieNode node, ArrayList<String> result) {
		if (node == null) {
			// if end of subtree, return
			return;
		} else {
			// check left subtree
			inOrderTraversal(node.left, result);
			// if node is an end of string, add it to the result array list
			if (node.isKey == true) {
				result.add(node.str);
			}
			// check right subtree
			inOrderTraversal(node.right, result);
		}
	}*/

	/**
	 * Finds the longest {@link String} stored in the Binary Patricia Trie.
	 * 
	 * @return
	 *         <p>
	 *         The longest {@link String} stored in this. If the trie is empty, the
	 *         empty string &quot;&quot; should be returned. Careful: the empty
	 *         string &quot;&quot;is <b>not</b> the same string as &quot; &quot;;
	 *         the latter is a string consisting of a single <b>space character</b>!
	 *         It is also <b>not the same as the</b> null <b>reference</b>!
	 *         </p>
	 *
	 *         <p>
	 *         Ties should be broken in terms of <b>value</b> of the bit string. For
	 *         example, if our trie contained only the binary strings 01 and 11,
	 *         <b>11</b> would be the longest string. If our trie contained only 001
	 *         and 010, <b>010</b> would be the longest string.
	 *         </p>
	 */
	public String getLongest() {
		if(root.left == null && root.right == null) {
			return "";
		}
		return getLongest(root, "");
	}

	private String getLongest(TrieNode node, String longest) {
		// reached end of longest string so return
		if (node == null) {
			return longest;
		} else {
			// append 0 to longest when going left
			String leftLongest = getLongest(node.left, longest + node.str);
			// append 1 to longest when going right
			String rightLongest = getLongest(node.right, longest + node.str);

			if (leftLongest.length() > rightLongest.length()) {
				return leftLongest;
			} else {
				return rightLongest;
			}
		}
	}

	/**
	 * Makes sure that your trie doesn't have splitter nodes with a single child. In
	 * a Patricia trie, those nodes should be pruned.
	 * 
	 * @return {@code true} iff all nodes in the trie either denote stored strings
	 *         or split into two subtrees, {@code false} otherwise.
	 */
	public boolean isJunkFree() {
		return isEmpty() || (isJunkFree(root.left) && isJunkFree(root.right));
	}

	private boolean isJunkFree(TrieNode n) {
		if (n == null) { // Null subtrees trivially junk-free
			return true;
		}
		if (!n.isKey) { // Non-key nodes need to be strict splitter nodes
			return ((n.left != null) && (n.right != null) && isJunkFree(n.left) && isJunkFree(n.right));
		} else {
			return (isJunkFree(n.left) && isJunkFree(n.right)); // But key-containing nodes need not.
		}
	}
}
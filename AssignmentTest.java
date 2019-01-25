import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import textbook.LinkedBinaryTree;

public class AssignmentTest {
	
	// Set up JUnit to be able to check for expected exceptions
	@Rule
	public ExpectedException thrown = ExpectedException.none();
/***********************
 * 
 * 
 * Test cases that I designed are after the provided test cases
 * 
 * ***********************
 */
	// Some simple testing of prefix2tree
	@Test(timeout = 100)
	public void testPrefix2tree() {
		
		LinkedBinaryTree<String> tree;

		tree = Assignment.prefix2tree("hi");
		assertEquals(1, tree.size());
		assertEquals("hi", tree.root().getElement());

		tree = Assignment.prefix2tree("+ 5 10");
		assertEquals(3, tree.size());
		assertEquals("+", tree.root().getElement());
		assertEquals("5", tree.left(tree.root()).getElement());
		assertEquals("10", tree.right(tree.root()).getElement());
		
		tree = Assignment.prefix2tree("- 5 10");
		assertEquals(3, tree.size());
		assertEquals("-", tree.root().getElement());
		assertEquals("5", tree.left(tree.root()).getElement());
		assertEquals("10", tree.right(tree.root()).getElement());
		
		tree = Assignment.prefix2tree("* 5 10");
		assertEquals(3, tree.size());
		assertEquals("*", tree.root().getElement());
		assertEquals("5", tree.left(tree.root()).getElement());
		assertEquals("10", tree.right(tree.root()).getElement());
				
		tree = Assignment.prefix2tree("+ 5 - 4 3");
		assertEquals(5, tree.size());
		assertEquals("+", tree.root().getElement());
		assertEquals("5", tree.left(tree.root()).getElement());
		assertEquals("-", tree.right(tree.root()).getElement());
		assertEquals("4", tree.left(tree.right(tree.root())).getElement());
		assertEquals("3", tree.right(tree.right(tree.root())).getElement());
		
		thrown.expect(IllegalArgumentException.class);
		tree = Assignment.prefix2tree("+ 5 - 4");
	}
/**************************
 * Tests designed by myself
 **************************/
//tests for tree2prefix
	@Test(timeout = 100)
	public void testTree2prefix() {
		LinkedBinaryTree<String> tree = Assignment.prefix2tree("- + -2 c 2");
		assertEquals("- + -2 c 2", Assignment.tree2prefix(tree));
		
		LinkedBinaryTree<String> tree1 = Assignment.prefix2tree("- x + 1 2");
		assertEquals("- x + 1 2", Assignment.tree2prefix(tree1));
		
		LinkedBinaryTree<String> tree2 = Assignment.prefix2tree("- x * -7 - c + 6 5");
		assertEquals("- x * -7 - c + 6 5", Assignment.tree2prefix(tree2));
		
	}
	@Test(timeout = 100) //invalid expression
	public void testTree2prefixException() {
		thrown.expect(IllegalArgumentException.class);
		LinkedBinaryTree<String> tree=new LinkedBinaryTree<String>();
		tree.addRoot("+");
		tree.addLeft(tree.root(), "1");
		String prefix = Assignment.tree2prefix(tree);
	}
//tests for tree2infix
	@Test(timeout = 100)
	public void testTree2infix() {
		LinkedBinaryTree<String> tree = Assignment.prefix2tree("- + -2 c 2");
		assertEquals("((-2+c)-2)", Assignment.tree2infix(tree));
		
		LinkedBinaryTree<String> tree1 = Assignment.prefix2tree("- x + 1 2");
		assertEquals("(x-(1+2))", Assignment.tree2infix(tree1));
		
		LinkedBinaryTree<String> tree2 = Assignment.prefix2tree("- x * -7 - 8 + 6 5");
		assertEquals("(x-(-7*(8-(6+5))))", Assignment.tree2infix(tree2));
		
	}
	@Test(timeout = 100)  //invalid expression
	public void testTree2infixException() {
		thrown.expect(IllegalArgumentException.class);
		LinkedBinaryTree<String> tree=new LinkedBinaryTree<String>();
		tree.addRoot("+");
		tree.addLeft(tree.root(), "-");
		tree.addRight(tree.root(), "1");
		String infix = Assignment.tree2infix(tree);
	}
// tests for simplify
	@Test(timeout = 100) //normal checking using simple tree input
	public void testSimplify1() {
		LinkedBinaryTree<String> tree = Assignment.prefix2tree("- + 1 2 1");
		tree = Assignment.simplify(tree);
		LinkedBinaryTree<String> expected = Assignment.prefix2tree("2");
		assertTrue(Assignment.equals(tree, expected));
	}
	@Test(timeout = 100) //correct output when the tree contain letter
	public void testSimplify2() {
		LinkedBinaryTree<String> tree = Assignment.prefix2tree("- x + 3 * 7 - 8 9");
		tree = Assignment.simplify(tree);
		LinkedBinaryTree<String> expected = Assignment.prefix2tree("- x -4");
		assertTrue(Assignment.equals(tree, expected));
	}
	@Test(timeout = 100)
	public void testSimplify3() {
		LinkedBinaryTree<String> tree = Assignment.prefix2tree("- -1 + 3 * 7 - 8 9");
		tree = Assignment.simplify(tree);
		LinkedBinaryTree<String> expected = Assignment.prefix2tree("3");
		assertTrue(Assignment.equals(tree, expected));
	}
	@Test(timeout = 100)
	public void testSimplify4() {
		LinkedBinaryTree<String> tree = Assignment.prefix2tree("* -1 + 3 * 7 - 8 9");
		tree = Assignment.simplify(tree);
		LinkedBinaryTree<String> expected = Assignment.prefix2tree("4");
		assertTrue(Assignment.equals(tree, expected));
	}
	@Test(timeout = 100)
	public void testSimplify5() {
		LinkedBinaryTree<String> tree = Assignment.prefix2tree("+ 2 + 3 * 7 - 8 9");
		tree = Assignment.simplify(tree);
		LinkedBinaryTree<String> expected = Assignment.prefix2tree("-2");
		assertTrue(Assignment.equals(tree, expected));
	}
	@Test(timeout = 100)
	public void testSimplify6() {
		LinkedBinaryTree<String> tree = Assignment.prefix2tree("- 0 2");
		tree = Assignment.simplify(tree);
		LinkedBinaryTree<String> expected = Assignment.prefix2tree("-2");
		assertTrue(Assignment.equals(tree, expected));
	}
	@Test(timeout = 100)
	public void testSimplify7() {
		LinkedBinaryTree<String> tree = Assignment.prefix2tree("- - 1 5 + 2 * c 8");
		tree = Assignment.simplify(tree);
		LinkedBinaryTree<String> expected = Assignment.prefix2tree("- -4 + 2 * c 8");
		assertTrue(Assignment.equals(tree, expected));
	}
	@Test(timeout = 100)
	public void testSimplify8() {
		LinkedBinaryTree<String> tree = Assignment.prefix2tree("- c + b * d a");
		tree = Assignment.simplify(tree);
		LinkedBinaryTree<String> expected = Assignment.prefix2tree("- c + b * d a");
		assertTrue(Assignment.equals(tree, expected));
	}
	@Test(timeout = 100) //invalid expression
	public void testSimplifyException() {
		thrown.expect(IllegalArgumentException.class);
		LinkedBinaryTree<String> tree1=new LinkedBinaryTree<String>();
		tree1.addRoot("+");
		tree1.addLeft(tree1.root(), "1");
		LinkedBinaryTree<String> test1 = Assignment.simplify(tree1);
		
		thrown.expect(IllegalArgumentException.class);
		LinkedBinaryTree<String> tree2=new LinkedBinaryTree<String>();
		tree2.addRoot("+");
		tree2.addLeft(tree2.root(), "1");
		tree2.addRight(tree2.root(),"-");
		LinkedBinaryTree<String> test2 = Assignment.simplify(tree2);
		
		thrown.expect(IllegalArgumentException.class);
		LinkedBinaryTree<String> tree3=new LinkedBinaryTree<String>();
		tree3=null;
		LinkedBinaryTree<String> test3 = Assignment.simplify(tree3);
	}
	
//tests for simplifyFancy
	@Test(timeout = 100)
	//subtract 2 same sub tree with letter
	public void testSimplifyFancy1() {
		LinkedBinaryTree<String> tree = Assignment.prefix2tree("- + c 2 + c 2");
		tree = Assignment.simplifyFancy(tree);
		LinkedBinaryTree<String> expected = Assignment.prefix2tree("0");
		assertTrue(Assignment.equals(tree, expected));
	}
	//subtree+0
	@Test(timeout = 100)
	public void testSimplifyFancy2() {
		LinkedBinaryTree<String> tree = Assignment.prefix2tree("+ * c 2 0");
		tree = Assignment.simplifyFancy(tree);
		LinkedBinaryTree<String> expected = Assignment.prefix2tree("* c 2");
		assertTrue(Assignment.equals(tree, expected));
	}
    //0+subtree
	@Test(timeout = 100)
	public void testSimplifyFancy3() {
		LinkedBinaryTree<String> tree = Assignment.prefix2tree("+ 0 * c 3");
		tree = Assignment.simplifyFancy(tree);
		LinkedBinaryTree<String> expected = Assignment.prefix2tree("* c 3");
		assertTrue(Assignment.equals(tree, expected));
	}
	//subtree-0
	@Test(timeout = 100)
	public void testSimplifyFancy4() {
		LinkedBinaryTree<String> tree = Assignment.prefix2tree("- * c 2 0");
		tree = Assignment.simplifyFancy(tree);
		LinkedBinaryTree<String> expected = Assignment.prefix2tree("* c 2");
		assertTrue(Assignment.equals(tree, expected));
	}
	//0-subtree
	@Test(timeout = 100)
	public void testSimplifyFancy5() {
		LinkedBinaryTree<String> tree = Assignment.prefix2tree("- 0 * c 2");
		tree = Assignment.simplifyFancy(tree);
		LinkedBinaryTree<String> expected = Assignment.prefix2tree("- 0 * c 2");
		assertTrue(Assignment.equals(tree, expected));
		
		LinkedBinaryTree<String> tree2 = Assignment.prefix2tree("- 0 c");
		tree2 = Assignment.simplifyFancy(tree2);
		LinkedBinaryTree<String> expected2 = Assignment.prefix2tree("- 0 c");
		assertTrue(Assignment.equals(tree2, expected2));
	}
	//1*subtree && subtree*1
	@Test(timeout = 100)
	public void testSimplifyFancy6() {
		LinkedBinaryTree<String> tree = Assignment.prefix2tree("* 1 + c 2");
		tree = Assignment.simplifyFancy(tree);
		LinkedBinaryTree<String> expected = Assignment.prefix2tree("+ c 2");
		assertTrue(Assignment.equals(tree, expected));
		
		LinkedBinaryTree<String> tree2 = Assignment.prefix2tree("* + c 2 1");
		tree2 = Assignment.simplifyFancy(tree2);
		LinkedBinaryTree<String> expected2 = Assignment.prefix2tree("+ c 2");
		assertTrue(Assignment.equals(tree2, expected2));
	}
	//0*subtree && subtree*0
	@Test(timeout = 100)
	public void testSimplifyFancy7() {
		LinkedBinaryTree<String> tree = Assignment.prefix2tree("* 0 + c 2");
		tree = Assignment.simplifyFancy(tree);
		LinkedBinaryTree<String> expected = Assignment.prefix2tree("0");
		assertTrue(Assignment.equals(tree, expected));
		
		LinkedBinaryTree<String> tree2 = Assignment.prefix2tree("* + c 2 0");
		tree2 = Assignment.simplifyFancy(tree2);
		LinkedBinaryTree<String> expected2 = Assignment.prefix2tree("0");
		assertTrue(Assignment.equals(tree2, expected2));
	}
	//complicated tree
	@Test(timeout = 100)
	public void testSimplifyFancy8() {
		LinkedBinaryTree<String> tree = Assignment.prefix2tree("* + 3 4 * c - 7 7");
		tree = Assignment.simplifyFancy(tree);
		LinkedBinaryTree<String> expected = Assignment.prefix2tree("0");
		assertTrue(Assignment.equals(tree, expected));
	}
	//complicated tree
		@Test(timeout = 100)
		public void testSimplifyFancy9() {
			LinkedBinaryTree<String> tree = Assignment.prefix2tree("* + 3 4 * -1 - 7 6");
			tree = Assignment.simplifyFancy(tree);
			LinkedBinaryTree<String> expected = Assignment.prefix2tree("-7");
			assertTrue(Assignment.equals(tree, expected));
		}
		@Test(timeout = 100) //invalid expression
		public void testSimplifyFancyException() {
			thrown.expect(IllegalArgumentException.class);
			LinkedBinaryTree<String> tree=new LinkedBinaryTree<String>();
			tree.addRoot("+");
			tree.addLeft(tree.root(), "1");
			LinkedBinaryTree<String> test = Assignment.simplifyFancy(tree);
			

			thrown.expect(IllegalArgumentException.class);
			LinkedBinaryTree<String> tree2=new LinkedBinaryTree<String>();
			tree2.addRoot("+");
			tree2.addLeft(tree2.root(), "1");
			tree2.addRight(tree2.root(),"-");
			LinkedBinaryTree<String> test2 = Assignment.simplifyFancy(tree2);
			
			thrown.expect(IllegalArgumentException.class);
			LinkedBinaryTree<String> tree3=new LinkedBinaryTree<String>();
			tree3=null;
			LinkedBinaryTree<String> test3 = Assignment.simplifyFancy(tree3);	
		}
//Tests for substitute
		@Test(timeout = 100)
		public void testSubstitue() {
			LinkedBinaryTree<String> tree = Assignment.prefix2tree("* + 3 4 * -1 - c c");
			tree = Assignment.substitute(tree,"c",7);
			LinkedBinaryTree<String> expected = Assignment.prefix2tree("* + 3 4 * -1 - 7 7");
			assertTrue(Assignment.equals(tree, expected));
			
			LinkedBinaryTree<String> tree2 = Assignment.prefix2tree("* + d d * -1 - c c");
			tree2 = Assignment.substitute(tree2,"d",1);
			LinkedBinaryTree<String> expected2 = Assignment.prefix2tree("* + 1 1 * -1 - c c");
			assertTrue(Assignment.equals(tree2, expected2));
			
			LinkedBinaryTree<String> tree3 = Assignment.prefix2tree("* + 3 4 * -1 - c c");
			tree3 = Assignment.substitute(tree3,"c",-7);
			LinkedBinaryTree<String> expected3 = Assignment.prefix2tree("* + 3 4 * -1 - -7 -7");
			assertTrue(Assignment.equals(tree3, expected3));
		}
		//test invalid tree input
		@Test(timeout = 100)
		public void testSubstituteException1() {
			thrown.expect(IllegalArgumentException.class);
			LinkedBinaryTree<String> test=new LinkedBinaryTree<>();
			test.addRoot("+");
			test.addLeft(test.root(), "c");
			LinkedBinaryTree<String> tree = Assignment.substitute(test, "c", 1);
		}
		//test invalid variable
		@Test(timeout = 100)
		public void testSubstituteException2() {
			thrown.expect(IllegalArgumentException.class);
			LinkedBinaryTree<String> test=new LinkedBinaryTree<>();
			test.addRoot("+");
			test.addLeft(test.root(), "c");
			test.addRight(test.root(), "c");
			LinkedBinaryTree<String> tree = Assignment.substitute(test, "+", 1);
		}
		//test invalid variable
				@Test(timeout = 100)
				public void testSubstituteException3() {
					thrown.expect(IllegalArgumentException.class);
					LinkedBinaryTree<String> test=new LinkedBinaryTree<>();
					test.addRoot("+");
					test.addLeft(test.root(), "5");
					test.addRight(test.root(), "5");
					LinkedBinaryTree<String> tree = Assignment.substitute(test, "5", 1);
				}
				//test invalid variable
				@Test(timeout = 100)
				public void testSubstituteException4() {
					thrown.expect(IllegalArgumentException.class);
					LinkedBinaryTree<String> test=new LinkedBinaryTree<>();
					test.addRoot("+");
					test.addLeft(test.root(), "c");
					test.addRight(test.root(), "c");
					LinkedBinaryTree<String> tree = Assignment.substitute(test,null, 1);
				}
	//Tests for substitute Map
		@Test(timeout = 100)
		public void testSubstitueMap1() {
			LinkedBinaryTree<String> tree = Assignment.prefix2tree("* + a b * b - c c");
			HashMap<String,Integer> hsTest= new HashMap<String,Integer>();
			hsTest.put("a", 3);
			hsTest.put("b", 4);
			hsTest.put("c", 7);
			tree = Assignment.substitute(tree,hsTest);
			LinkedBinaryTree<String> expected = Assignment.prefix2tree("* + 3 4 * 4 - 7 7");
			assertTrue(Assignment.equals(tree, expected));
		}
		@Test(timeout = 100)
		public void testSubstitueMap2() {
			LinkedBinaryTree<String> tree = Assignment.prefix2tree("* + a b * b - c c");
			HashMap<String,Integer> hsTest= new HashMap<String,Integer>();
			hsTest.put("a", 3);
			hsTest.put("b", 4);
			hsTest.put("c", -7);
			tree = Assignment.substitute(tree,hsTest);
			LinkedBinaryTree<String> expected = Assignment.prefix2tree("* + 3 4 * 4 - -7 -7");
			assertTrue(Assignment.equals(tree, expected));
		}
		@Test(timeout = 100) //input tree is invalid
		public void testSubstitueMapException() {
			thrown.expect(IllegalArgumentException.class);
			LinkedBinaryTree<String> tree = new LinkedBinaryTree<>();
			tree.addRoot("+");
			tree.addLeft(tree.root(), "+");
			tree.addRight(tree.root(), "-");
			tree.addLeft(tree.left(tree.root()), "b");
			tree.addRight(tree.left(tree.root()), "c");
			HashMap<String,Integer> hsTest= new HashMap<String,Integer>();
			hsTest.put("a", 3);
			hsTest.put("b", 4);
			hsTest.put("c", 7);
			tree = Assignment.substitute(tree,hsTest);
			
		}
		
		//test isArithmeticException
		@Test(timeout = 100) //input tree is invalid
		public void testisArithmeticException() {
			LinkedBinaryTree<String> tree = new LinkedBinaryTree<>();
			tree.addRoot("+");
			tree.addLeft(tree.root(), "+");
			tree.addRight(tree.root(), "-");
			tree.addLeft(tree.left(tree.root()), "b");
			tree.addRight(tree.left(tree.root()), "c");
			assertFalse(Assignment.isArithmeticExpression(tree));
			
			LinkedBinaryTree<String> tree2 = new LinkedBinaryTree<>();
			tree2.addRoot("+");
			tree2.addLeft(tree2.root(), "+");
			tree2.addLeft(tree2.left(tree2.root()), "b");
			tree2.addRight(tree2.left(tree2.root()), "c");
			tree2.addRight(tree2.root(), "-");
			tree2.addLeft(tree2.right(tree2.root()), "b");
			tree2.addRight(tree2.right(tree2.root()), "c");
			assertTrue(Assignment.isArithmeticExpression(tree2));
			
		}
		
//Complicated Mixed Function test
		@Test(timeout = 100) // mixed simplifyFancy,prefix2tree and substitute 
		public void testMixed1() {
			LinkedBinaryTree<String> tree = Assignment.prefix2tree("+ * 3 4 * -1 - c c");
			tree = Assignment.substitute(tree,"c",7);
			tree = Assignment.simplifyFancy(tree);
			LinkedBinaryTree<String> expected = Assignment.prefix2tree("12");
			assertTrue(Assignment.equals(tree, expected));
		}
		@Test(timeout = 100) // mixed simplifyFancy,prefix2tree and substituteMap 
		public void testMixed2() {
			LinkedBinaryTree<String> tree = Assignment.prefix2tree("+ * a b * d - c c");
			HashMap<String,Integer> hsTest= new HashMap<String,Integer>();
			hsTest.put("a", 3);
			hsTest.put("b", 4);
			hsTest.put("c", 7);
			hsTest.put("d", -1);
			tree=Assignment.substitute(tree, hsTest);
			tree = Assignment.simplifyFancy(tree);
			LinkedBinaryTree<String> expected = Assignment.prefix2tree("12");
			assertTrue(Assignment.equals(tree, expected));
		}
	
}
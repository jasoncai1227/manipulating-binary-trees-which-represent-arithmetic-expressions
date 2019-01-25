import java.util.HashMap;
import textbook.LinkedBinaryTree;
import textbook.LinkedQueue;
import textbook.Position;

public class Assignment {

	/**
	 * Convert an arithmetic expression (in prefix notation), to a binary tree
	 * 
	 * Binary operators are +, -, * (i.e. addition, subtraction, multiplication)
	 * Anything else is assumed to be a variable or numeric value
	 * 
	 * Example: "+ 2 15" will be a tree with root "+", left child "2" and right
	 * child "15" i.e. + 2 15
	 * 
	 * Example: "+ 2 - 4 5" will be a tree with root "+", left child "2", right
	 * child a subtree representing "- 4 5" i.e. + 2 - 4 5
	 * 
	 * This method runs in O(n) time
	 * 
	 * @param expression
	 *            - an arithmetic expression in prefix notation
	 * @return BinaryTree representing an expression expressed in prefix
	 *         notation
	 * @throws IllegalArgumentException
	 *             if expression was not a valid expression
	 */
	public static LinkedBinaryTree<String> prefix2tree(String expression) throws IllegalArgumentException {
		if (expression == null) {
			throw new IllegalArgumentException("Expression string was null");
		}
		// break up the expression string using spaces, into a queue
		LinkedQueue<String> tokens = new LinkedQueue<String>();
		for (String token : expression.split(" ")) {
			tokens.enqueue(token);
		}
		// recursively build the tree
		return prefix2tree(tokens);
	}
	
	/**
	 * Recursive helper method to build an tree representing an arithmetic
	 * expression in prefix notation, where the expression has already been
	 * broken up into a queue of tokens
	 * 
	 * @param tokens
	 * @return
	 * @throws IllegalArgumentException
	 *             if expression was not a valid expression
	 */
	private static LinkedBinaryTree<String> prefix2tree(LinkedQueue<String> tokens) throws IllegalArgumentException {
		LinkedBinaryTree<String> tree = new LinkedBinaryTree<String>();

		// use the next element of the queue to build the root
		if (tokens.isEmpty()) {
			throw new IllegalArgumentException("String was not a valid arithmetic expression in prefix notation");
		}
		String element = tokens.dequeue();
		tree.addRoot(element);

		// if the element is a binary operation, we need to build the left and
		// right subtrees
		if (element.equals("+") || element.equals("-") || element.equals("*")) {
			LinkedBinaryTree<String> left = prefix2tree(tokens);
			LinkedBinaryTree<String> right = prefix2tree(tokens);
			tree.attach(tree.root(), left, right);
		}
		// otherwise, assume it's a variable or a value, so it's a leaf (i.e.
		// nothing more to do)

		return tree;
	}
	
	/**
	 * Test to see if two trees are identical (every position in the tree stores the same value)
	 * 
	 * e.g. two trees representing "+ 1 2" are identical to each other, but not to a tree representing "+ 2 1"
	 * @param a
	 * @param b
	 * @return true if the trees have the same structure and values, false otherwise
	 */
	public static boolean equals(LinkedBinaryTree<String> a, LinkedBinaryTree<String> b) {
		return equals(a, b, a.root(), b.root());
	}

	/**
	 * Recursive helper method to compare two trees
	 * @param aTree one of the trees to compare
	 * @param bTree the other tree to compare
	 * @param aRoot a position in the first tree
	 * @param bRoot a position in the second tree (corresponding to a position in the first)
	 * @return true if the subtrees rooted at the given positions are identical
	 */
	private static boolean equals(LinkedBinaryTree<String> aTree, LinkedBinaryTree<String> bTree, Position<String> aRoot, Position<String> bRoot) {
		//if either of the positions is null, then they are the same only if they are both null
		if(aRoot == null || bRoot == null) {
			return (aRoot == null) && (bRoot == null);
		}
		//first check that the elements stored in the current positions are the same
		String a = aRoot.getElement();
		String b = bRoot.getElement();
		if((a==null && b==null) || a.equals(b)) {
			//then recursively check if the left subtrees are the same...
			boolean left = equals(aTree, bTree, aTree.left(aRoot), bTree.left(bRoot));
			//...and if the right subtrees are the same
			boolean right = equals(aTree, bTree, aTree.right(aRoot), bTree.right(bRoot));
			//return true if they both matched
			return left && right;
		}
		else {
			return false;
		}
	}

	
	/**
	 * Given a tree, this method should output a string for the corresponding
	 * arithmetic expression in prefix notation, without (parenthesis) (also
	 * known as Polish notation)
	 * 
	 * Example: A tree with root "+", left child "2" and right child "15" would
	 * be "+ 2 15" Example: A tree with root "-", left child a subtree
	 * representing "(2+15)" and right child "4" would be "- + 2 15 4"
	 * 
	 * Ideally, this method should run in O(n) time
	 * 
	 * @param tree
	 *            - a tree representing an arithmetic expression
	 * @return prefix notation expression of the tree
	 * @throws IllegalArgumentException
	 *             if tree was not a valid expression
	 */
	public static String tree2prefix(LinkedBinaryTree<String> tree) throws IllegalArgumentException {
	    if(!isArithmeticExpression(tree)){
	    	throw new IllegalArgumentException();
	    }
	    return tree2prefix(tree,tree.root());
	}
	
	//helper method tree2prefix
	private static String tree2prefix(LinkedBinaryTree<String> tree, Position<String> root) throws IllegalArgumentException{
		if(!tree.isExternal(root)){
			return root.getElement()+" "+tree2prefix(tree,tree.left(root))+" "+tree2prefix(tree,tree.right(root));
		}
		else
		return root.getElement();
	}

	/**
	 * Given a tree, this method should output a string for the corresponding
	 * arithmetic expression in infix notation with parenthesis (i.e. the most
	 * commonly used notation).
	 * 
	 * Example: A tree with root "+", left child "2" and right child "15" would
	 * be "(2+15)"
	 * 
	 * Example: A tree with root "-", left child a subtree representing "(2+15)"
	 * and right child "4" would be "((2+15)-4)"
	 * 
	 * Optionally, you may leave out the outermost parenthesis, but it's fine to
	 * leave them on. (i.e. "2+15" and "(2+15)-4" would also be acceptable
	 * output for the examples above)
	 * 
	 * Ideally, this method should run in O(n) time
	 * 
	 * @param tree
	 *            - a tree representing an arithmetic expression
	 * @return infix notation expression of the tree
	 * @throws IllegalArgumentException
	 *             if tree was not a valid expression
	 */
	public static String tree2infix(LinkedBinaryTree<String> tree) throws IllegalArgumentException {
		 if(!isArithmeticExpression(tree)){
		    	throw new IllegalArgumentException();
		    }
		return tree2infix(tree,tree.root());
	}
	
	//helper method for tree2infix
	private static String tree2infix(LinkedBinaryTree<String> tree,Position<String> root) throws IllegalArgumentException{
		if(!tree.isExternal(root)){
			
			return "("+tree2infix(tree,tree.left(root))+root.getElement()+tree2infix(tree,tree.right(root))+")";
		}
		else
			
		return root.getElement();
	}

	/**
	 * Given a tree, this method should simplify any subtrees which can be
	 * evaluated to a single integer value.
	 * 
	 * Ideally, this method should run in O(n) time
	 * 
	 * @param tree
	 *            - a tree representing an arithmetic expression
	 * @return resulting binary tree after evaluating as many of the subtrees as
	 *         possible
	 * @throws IllegalArgumentException
	 *             if tree was not a valid expression
	 */
	public static LinkedBinaryTree<String> simplify(LinkedBinaryTree<String> tree) throws IllegalArgumentException {
		if(!isArithmeticExpression(tree)){
			throw new IllegalArgumentException();
		}
		return simplify(tree,tree.root());
	}
	
	//helper method for simplify
	private static LinkedBinaryTree<String> simplify(LinkedBinaryTree<String> tree,Position<String> root) throws IllegalArgumentException {
		//temporary tree
		LinkedBinaryTree<String> newtree=new LinkedBinaryTree<String>();
		if(!tree.isExternal(root)){
			LinkedBinaryTree<String> lefttree=simplify(tree,tree.left(root));
			LinkedBinaryTree<String> righttree=simplify(tree,tree.right(root));
		if(root.getElement().equals("+")){
			/*if the lefttree has only one node which is a letter
			 * the expression cannot be simplified
			*/
		   if(Character.isLetter(lefttree.root().getElement().charAt(0))){
		   newtree.addRoot("+");
		   newtree.attach(newtree.root(),lefttree,righttree);
		   return newtree;
		    }
		   /*if the righttree has only one node which is a letter
			 * the expression cannot be simplified
			*/
		    if(Character.isLetter(righttree.root().getElement().charAt(0))){
		    newtree.addRoot("+");
		    newtree.attach(newtree.root(),lefttree,righttree);
		    return newtree;
		    }
		    /*if the lefttree is not a single node tree
			 * the expression cannot be simplified
			*/
		    if(lefttree.size()!=1){
		    	newtree.addRoot("+");
		    	newtree.attach(newtree.root(),lefttree,righttree);
			    return newtree;
		    }
		    /*if the righttree is not a single node tree
			 * the expression cannot be simplified
			*/
		    if(righttree.size()!=1){
		    	newtree.addRoot("+");
		    	newtree.attach(newtree.root(),lefttree,righttree);
			    return newtree;
		    }
		    /*if both subtree are single node trees and the element of the nodes are both negative integer
			 * the expression can be simplified, but should be careful for the sign
			*/
		    if(lefttree.root().getElement().charAt(0)=='-'&&righttree.root().getElement().charAt(0)=='-'){
		    	int result=Integer.parseInt(lefttree.root().getElement().substring(1))+Integer.parseInt(righttree.root().getElement().substring(1));
		    	newtree.addRoot("-"+result+"");
				return newtree;
		    }
		    //lefttree is a single node tree and the element of the node is negative number
		    if(lefttree.root().getElement().charAt(0)=='-'){
		    	int result=Integer.parseInt(righttree.root().getElement())-Integer.parseInt(lefttree.root().getElement().substring(1));
		    	newtree.addRoot(result+"");
				return newtree;
		    }
		  //righttree is a single node tree and the element of the node is negative number
		    if(righttree.root().getElement().charAt(0)=='-'){
		    	int result=Integer.parseInt(lefttree.root().getElement())-Integer.parseInt(righttree.root().getElement().substring(1));
		    	newtree.addRoot(result+"");
				return newtree;
		    }
			int result=Integer.parseInt(lefttree.root().getElement())+Integer.parseInt(righttree.root().getElement());
			newtree.addRoot(result+"");
			return newtree;
			}
			if(root.getElement().equals("-")){
				/*if the lefttree has only one node which is a letter
				 * the expression cannot be simplified
				*/
				 if(Character.isLetter(lefttree.root().getElement().charAt(0))){
					 newtree.addRoot("-");
				newtree.attach(newtree.root(),lefttree,righttree);
			    return newtree;
			       }
				 /*if the righttree has only one node which is a letter
					 * the expression cannot be simplified
					*/
			     if(Character.isLetter(righttree.root().getElement().charAt(0))){
				    newtree.addRoot("-");
				    newtree.attach(newtree.root(),lefttree,righttree);
					return newtree;
					    }
			     /*if the lefttree is not a single node tree
					 * the expression cannot be simplified
					*/
			    if(lefttree.size()!=1){
			    	newtree.addRoot("-");
			    	newtree.attach(newtree.root(),lefttree,righttree);
				    return newtree;
			    }
			    /*if the righttree is not a single node tree
				 * the expression cannot be simplified
				*/
			    if(righttree.size()!=1){
			    	newtree.addRoot("-");
			    	newtree.attach(newtree.root(),lefttree,righttree);
				    return newtree;
			    }
			    /*if both subtree are single node trees and the element of the nodes are both negative integer
				 * the expression can be simplified, but should be careful for the sign
				*/
			    if(lefttree.root().getElement().charAt(0)=='-'&&righttree.root().getElement().charAt(0)=='-'){
			    	int result=Integer.parseInt(righttree.root().getElement().substring(1))-Integer.parseInt(lefttree.root().getElement().substring(1));
			    	newtree.addRoot(result+"");
					return newtree;
			    }
			  //lefttree is a single node tree and the element of the node is negative number
			    if(lefttree.root().getElement().charAt(0)=='-'){
			    	int result=Integer.parseInt(lefttree.root().getElement().substring(1))+Integer.parseInt(righttree.root().getElement());
			    	newtree.addRoot("-"+result+"");
					return newtree;
			    }
			  //righttree is a single node tree and the element of the node is negative number
			    if(righttree.root().getElement().charAt(0)=='-'){
			    	int result=Integer.parseInt(lefttree.root().getElement())+Integer.parseInt(righttree.root().getElement().substring(1));
			    	newtree.addRoot(result+"");
					return newtree;
			    }
				int result=Integer.parseInt(lefttree.root().getElement())-Integer.parseInt(righttree.root().getElement());
				newtree.addRoot(result+"");
				return newtree;
				}
			if(root.getElement().equals("*")){
				/*if the lefttree has only one node which is a letter
				 * the expression cannot be simplified
				*/
			 if(Character.isLetter(lefttree.root().getElement().charAt(0))){
					 newtree.addRoot("*");
					   newtree.attach(newtree.root(),lefttree,righttree);
					   return newtree;
			  }
			 /*if the righttree has only one node which is a letter
				 * the expression cannot be simplified
				*/
			 if(Character.isLetter(righttree.root().getElement().charAt(0))){
					   newtree.addRoot("*");
					    newtree.attach(newtree.root(),lefttree,righttree);
					    return newtree;
				 }
			 /*if the lefttree is not a single node tree
				 * the expression cannot be simplified
				*/
				if(lefttree.size()!=1){
					    	newtree.addRoot("*");
					    	newtree.attach(newtree.root(),lefttree,righttree);
						    return newtree;
			    }
				/*if the righttree is not a single node tree
				 * the expression cannot be simplified
				*/
			    if(righttree.size()!=1){
					    	newtree.addRoot("*");
					    	newtree.attach(newtree.root(),lefttree,righttree);
						    return newtree;
			     }
			    /*if both subtree are single node trees and the element of the nodes are both negative integer
				 * the expression can be simplified, but should be careful for the sign
				*/
			    if(lefttree.root().getElement().charAt(0)=='-'&&righttree.root().getElement().charAt(0)=='-'){
			    	int result=Integer.parseInt(righttree.root().getElement().substring(1))*Integer.parseInt(lefttree.root().getElement().substring(1));
			    	newtree.addRoot(result+"");
					return newtree;
			    }
			  //lefttree is a single node tree and the element of the node is negative number
			    if(lefttree.root().getElement().charAt(0)=='-'){
			    	int result=Integer.parseInt(lefttree.root().getElement().substring(1))*Integer.parseInt(righttree.root().getElement());
			    	newtree.addRoot("-"+result+"");
					return newtree;
			    }
			    //righttree is a single node tree and the element of the node is negative number
			    if(righttree.root().getElement().charAt(0)=='-'){
			    	int result=Integer.parseInt(lefttree.root().getElement())*Integer.parseInt(righttree.root().getElement().substring(1));
			    	newtree.addRoot("-"+result+"");
					return newtree;
			    }
				int result=Integer.parseInt(lefttree.root().getElement())*Integer.parseInt(righttree.root().getElement());
				newtree.addRoot(result+"");
				return newtree;
				}
		}
		else
			
		newtree.addRoot(root.getElement());
		return newtree;
		
	}
	/**
	 * This should do everything the simplify method does AND also apply the following rules:
	 *  * 1 x == x  i.e.  (1*x)==x
	 *  * x 1 == x        (x*1)==x
	 *  * 0 x == 0        (0*x)==0
	 *  * x 0 == 0        (x*0)==0
	 *  + 0 x == x        (0+x)==x
	 *  + x 0 == 0        (x+0)==x
	 *  - x 0 == x        (x-0)==x
	 *  - x x == 0        (x-x)==0
	 *  
	 *  Example: - * 1 x x == 0, in infix notation: ((1*x)-x) = (x-x) = 0
	 *  
	 * Ideally, this method should run in O(n) time (harder to achieve than for other methods!)
	 * 
	 * @param tree
	 *            - a tree representing an arithmetic expression
	 * @return resulting binary tree after applying the simplifications
	 * @throws IllegalArgumentException
	 *             if tree was not a valid expression
	 */
	public static LinkedBinaryTree<String> simplifyFancy(LinkedBinaryTree<String> tree) throws IllegalArgumentException {
		if(!isArithmeticExpression(tree)){
			throw new IllegalArgumentException();
		}
		return simplifyFancy(tree,tree.root());
	}
    //help method for simplifyFancy
	private static LinkedBinaryTree<String> simplifyFancy(LinkedBinaryTree<String> tree,Position<String> root){
		//temporary tree
		LinkedBinaryTree<String> newtree=new LinkedBinaryTree<String>();
		if(!tree.isExternal(root)){
			LinkedBinaryTree<String> lefttree=simplifyFancy(tree,tree.left(root));
			LinkedBinaryTree<String> righttree=simplifyFancy(tree,tree.right(root));
		    if(root.getElement().equals("+")){
		    	/*if the righttree has only one node which is 0
				 * X+0=X
				*/
		    	if(righttree.root().getElement().equals("0")){
					return lefttree;   
			   }
		    	/*if the lefttree has only one node which is 0
				 * 0+X=X
				*/
			   if(lefttree.root().getElement().equals("0")){
					return righttree;  
			   }
			   /*if the lefttree has only one node which is a letter
				 * the expression cannot be simplified
				*/
			   if(Character.isLetter(lefttree.root().getElement().charAt(0))){
				newtree.addRoot("+");
			    newtree.attach(newtree.root(),lefttree,righttree);
			   return newtree;
			   }
			   /*if the righttree has only one node which is a letter
				 * the expression cannot be simplified
				*/
			   if(Character.isLetter(righttree.root().getElement().charAt(0))){
				   newtree.addRoot("+");
				    newtree.attach(newtree.root(),lefttree,righttree);  
			  return newtree;
			   }
			   /*if the lefttree is not a single node tree
				 * the expression cannot be simplified
				*/
			   if(lefttree.size()!=1){
			    	newtree.addRoot("+");
			    	newtree.attach(newtree.root(),lefttree,righttree);
				    return newtree;
	    }
			   /*if the righttree is not a single node tree
				 * the expression cannot be simplified
				*/
	    if(righttree.size()!=1){
			    	newtree.addRoot("+");
			    	newtree.attach(newtree.root(),lefttree,righttree);
				    return newtree;
	     }
	    /*if both subtree are single node trees and the element of the nodes are both negative integer
		 * the expression can be simplified, but should be careful for the sign
		*/
	    if(lefttree.root().getElement().charAt(0)=='-'&&righttree.root().getElement().charAt(0)=='-'){
	    	int result=Integer.parseInt(lefttree.root().getElement().substring(1))+Integer.parseInt(righttree.root().getElement().substring(1));
	    	newtree.addRoot("-"+result+"");
			return newtree;
	    }
	  //lefttree is a single node tree and the element of the node is negative number
	    if(lefttree.root().getElement().charAt(0)=='-'){
	    	int result=Integer.parseInt(righttree.root().getElement())-Integer.parseInt(lefttree.root().getElement().substring(1));
	    	newtree.addRoot(result+"");
			return newtree;
	    }
	    //righttree is a single node tree and the element of the node is negative number
	    if(righttree.root().getElement().charAt(0)=='-'){
	    	int result=Integer.parseInt(lefttree.root().getElement())-Integer.parseInt(righttree.root().getElement().substring(1));
	    	newtree.addRoot(result+"");
			return newtree;
	    }
			int result=Integer.parseInt(lefttree.root().getElement())+Integer.parseInt(righttree.root().getElement());
			newtree.addRoot(result+"");
			return newtree;
			}
		if(root.getElement().equals("-")){
			/*both lefttree and righttree are single nodes tree and nodes are both letter
			 * if two letter is same
			 * c-c=0
			 */
			if(Character.isLetter(lefttree.root().getElement().charAt(0))&&equals(lefttree,righttree)){
				newtree.addRoot("0");
				return newtree; 
			}
			/*both lefttree and righttree are multiple nodes
			 * if two tree are same
			 * x-x=0
			 */
			if(equals(lefttree,righttree)){
			       newtree.addRoot("0");
					return newtree; 	
			}
			/*if the righttree has only one node which is 0
			 * X-0=X
			*/
		  if(righttree.root().getElement().equals("0")){
						return lefttree;  
		  }
		  /*if the lefttree has only one node which is 0
			 * 0-X=0-X
			 * cannot be simplified
			*/
		  if(lefttree.root().getElement()=="0"){
			    	newtree.addRoot("-");
			    	newtree.attach(newtree.root(),lefttree,righttree);
			    	return newtree;
		  }
		  /*if the lefttree has only one node which is a letter
			 * the expression cannot be simplified
			*/
		  if(Character.isLetter(lefttree.root().getElement().charAt(0))){
				newtree.addRoot("-");
			    newtree.attach(newtree.root(),lefttree,righttree);
			   return newtree;
			   }
		  /*if the righttree has only one node which is a letter
			 * the expression cannot be simplified
			*/
			   if(Character.isLetter(righttree.root().getElement().charAt(0))){
				   newtree.addRoot("-");
				    newtree.attach(newtree.root(),lefttree,righttree);  
			  return newtree;
			   }
			   /*if the lefttree is not a single node tree
				 * the expression cannot be simplified
				*/
			   if(lefttree.size()!=1){
			    	newtree.addRoot("-");
			    	newtree.attach(newtree.root(),lefttree,righttree);
				    return newtree;
	    }
			   /*if the righttree is not a single node tree
				 * the expression cannot be simplified
				*/
	    if(righttree.size()!=1){
			    	newtree.addRoot("-");
			    	newtree.attach(newtree.root(),lefttree,righttree);
				    return newtree;
	     }
	    /*if both subtree are single node trees and the element of the nodes are both negative integer
		 * the expression can be simplified, but should be careful for the sign
		*/
	    if(lefttree.root().getElement().charAt(0)=='-'&&righttree.root().getElement().charAt(0)=='-'){
	    	int result=Integer.parseInt(righttree.root().getElement().substring(1))-Integer.parseInt(lefttree.root().getElement().substring(1));
	    	newtree.addRoot(result+"");
			return newtree;
	    }
	  //lefttree is a single node tree and the element of the node is negative number
	    if(lefttree.root().getElement().charAt(0)=='-'){
	    	int result=Integer.parseInt(lefttree.root().getElement().substring(1))+Integer.parseInt(righttree.root().getElement());
	    	newtree.addRoot("-"+result+"");
			return newtree;
	    }
	  //righttree is a single node tree and the element of the node is negative number
	    if(righttree.root().getElement().charAt(0)=='-'){
	    	int result=Integer.parseInt(lefttree.root().getElement())+Integer.parseInt(righttree.root().getElement().substring(1));
	    	newtree.addRoot(result+"");
			return newtree;
	    }
	     int result=Integer.parseInt(lefttree.root().getElement())-Integer.parseInt(righttree.root().getElement());
				newtree.addRoot(result+"");
				return newtree;
		}
		if(root.getElement().equals("*")){
			/*if the righttree has only one node which is 0
			 * X*0=0
			*/
		      if(righttree.root().getElement().equals("0")){
					newtree.addRoot("0");
					return newtree;   
		        }
		      /*if the righttree has only one node which is 0
				 * 0*X=0
				*/
		      if(lefttree.root().getElement().equals("0")){
				   newtree.addRoot("0");
					return newtree;  
			   }
		      /*if the righttree has only one node which is 1
				 * 1*X=X
				*/
		       if(lefttree.root().getElement().equals("1")){
				 
					return righttree;  
			   }
		       /*if the righttree has only one node which is 1
				 * X*1=X
				*/
		       if(righttree.root().getElement().equals("1")){
					return lefttree;   
			   }
		       /*if the lefttree has only one node which is a letter
				 * the expression cannot be simplified
				*/
		       if(Character.isLetter(lefttree.root().getElement().charAt(0))){
					newtree.addRoot("*");
				    newtree.attach(newtree.root(),lefttree,righttree);
				   return newtree;
				   }
		       /*if the righttree has only one node which is a letter
				 * the expression cannot be simplified
				*/
				   if(Character.isLetter(righttree.root().getElement().charAt(0))){
					   newtree.addRoot("*");
					    newtree.attach(newtree.root(),lefttree,righttree);  
				  return newtree;
				   }
				   /*if the lefttree is not a single node tree
					 * the expression cannot be simplified
					*/
			if(lefttree.size()!=1){
				    	newtree.addRoot("*");
				    	newtree.attach(newtree.root(),lefttree,righttree);
					    return newtree;
		    }
			/*if the righttree is not a single node tree
			 * the expression cannot be simplified
			*/
		    if(righttree.size()!=1){
				    	newtree.addRoot("*");
				    	newtree.attach(newtree.root(),lefttree,righttree);
					    return newtree;
		     }
		    /*if both subtree are single node trees and the element of the nodes are both negative integer
			 * the expression can be simplified, but should be careful for the sign
			*/
		    if(lefttree.root().getElement().charAt(0)=='-'&&righttree.root().getElement().charAt(0)=='-'){
		    	int result=Integer.parseInt(righttree.root().getElement().substring(1))*Integer.parseInt(lefttree.root().getElement().substring(1));
		    	newtree.addRoot(result+"");
				return newtree;
		    }
		  //lefttree is a single node tree and the element of the node is negative number
		    if(lefttree.root().getElement().charAt(0)=='-'){
		    	int result=Integer.parseInt(lefttree.root().getElement().substring(1))*Integer.parseInt(righttree.root().getElement());
		    	newtree.addRoot("-"+result+"");
				return newtree;
		    }
		  //righttree is a single node tree and the element of the node is negative number
		    if(righttree.root().getElement().charAt(0)=='-'){
		    	int result=Integer.parseInt(lefttree.root().getElement())*Integer.parseInt(righttree.root().getElement().substring(1));
		    	newtree.addRoot("-"+result+"");
				return newtree;
		    }
				int result=Integer.parseInt(lefttree.root().getElement())*Integer.parseInt(righttree.root().getElement());
				newtree.addRoot(result+"");
				return newtree;
				}
		}
		else
			
		newtree.addRoot(root.getElement());
		return newtree;
		
	}
	
	/**
	 * Given a tree, a variable label and a value, this should replace all
	 * instances of that variable in the tree with the given value
	 * 
	 * Ideally, this method should run in O(n) time (quite hard! O(n^2) is easier.)
	 * 
	 * @param tree
	 *            - a tree representing an arithmetic expression
	 * @param variable
	 *            - a variable label that might exist in the tree
	 * @param value
	 *            - an integer value that the variable represents
	 * @return Tree after replacing all instances of the specified variable with
	 *         it's numeric value
	 * @throws IllegalArgumentException
	 *             if tree was not a valid expression, or either of the other
	 *             arguments are null
	 */
	public static LinkedBinaryTree<String> substitute(LinkedBinaryTree<String> tree, String variable, int value)
			throws IllegalArgumentException {
		if(!isArithmeticExpression(tree)||variable==null||variable.equals("+")||variable.equals("-")||variable.equals("*")||Character.isDigit(variable.charAt(0))){
			throw new IllegalArgumentException();
		}
		return substitute(tree,tree.root(),variable,value);
	}
	
	//helper method for substitute
	private static LinkedBinaryTree<String> substitute(LinkedBinaryTree<String> tree, Position<String> root,String variable, int value){
		LinkedBinaryTree<String> newtree=new LinkedBinaryTree<String>();
		if(!tree.isExternal(root)){
			newtree.addRoot(root.getElement());
			newtree.attach(newtree.root(), substitute(tree,tree.left(root),variable,value), substitute(tree,tree.right(root),variable,value));
			return newtree;
		}
		else
	    // if find the corresponding variable in tree nodes
		 if(root.getElement().equals(variable)){
			 newtree.addRoot(value+"");
			 return newtree;
		 }
		newtree.addRoot(root.getElement());
		return newtree;
	}

	/**
	 * Given a tree and a a map of variable labels to values, this should
	 * replace all instances of those variables in the tree with the
	 * corresponding given values
	 * 
	 * Ideally, this method should run in O(n) expected time
	 * 
	 * @param tree
	 *            - a tree representing an arithmetic expression
	 * @param map
	 *            - a map of variable labels to integer values
	 * @return Tree after replacing all instances of variables which are keys in
	 *         the map, with their numeric values
	 * @throws IllegalArgumentException
	 *             if tree was not a valid expression, or map is null, or tries
	 *             to substitute a null into the tree
	 */
	public static LinkedBinaryTree<String> substitute(LinkedBinaryTree<String> tree, HashMap<String, Integer> map)
			throws IllegalArgumentException {
		if(!isArithmeticExpression(tree)||map==null){
			throw new IllegalArgumentException();
		}
		return substitute(tree,map,tree.root());
	}
	//helper method for substitute
	private static LinkedBinaryTree<String> substitute(LinkedBinaryTree<String> tree, HashMap<String, Integer> map,Position<String> root){
		LinkedBinaryTree<String> newtree=new LinkedBinaryTree<String>();
		if(!tree.isExternal(root)){
			newtree.addRoot(root.getElement());
			newtree.attach(newtree.root(), substitute(tree,map,tree.left(root)), substitute(tree,map,tree.right(root)));
			return newtree;
		}
		else
		// if the map contain the tree nodes
		if(map.containsKey(root.getElement())){
			//the substitute cannot be null
			if(map.get(root.getElement())==null){
				throw new IllegalArgumentException();
			}
			newtree.addRoot(map.get(root.getElement())+"");
	 		return newtree;
		}
		newtree.addRoot(root.getElement());
		return newtree;
	}

	/**
	 * Given a tree, identify if that tree represents a valid arithmetic
	 * expression (possibly with variables)
	 * 
	 * Ideally, this method should run in O(n) expected time
	 * 
	 * @param tree
	 *            - a tree representing an arithmetic expression
	 * @return true if the tree is not null and it obeys the structure of an
	 *              arithmetic expression. Otherwise, it returns false
	 */
	public static boolean isArithmeticExpression(LinkedBinaryTree<String> tree) {
		// TODO: Implement this method
		if(tree==null){
			return false;
		}
		return isArithmeticExpression(tree,tree.root());
	}
	//helper method
	private static boolean isArithmeticExpression(LinkedBinaryTree<String> tree, Position<String> root){
		if(!tree.isExternal(root)){
			//the node only have one child, which is invalid arithmetic expression e.g 4 +
			if(tree.numChildren(root)==1){
				return false;
			}
			if(isArithmeticExpression(tree,tree.left(root))&&isArithmeticExpression(tree,tree.right(root))){
				return true;
			}
			else
				return false;
		}
		else
			// leaf cannot be operators  e.g 4 + +   invalid expression
			if(root.getElement().equals("+")||root.getElement().equals("-")||root.getElement().equals("*")){
				return false;
			}
		return true;
	}

}


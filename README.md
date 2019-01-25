# static-methods-working-with-binary-trees

Notation

Values and variables

Any strings which are integers are values. Anything that is not an integer or an operator (see below) is to be considered a variable (although in the automarking we will only use single letters for variable labels, i.e. “x”, “c”, etc.)

Operators

Our operators are all binary operators (they take exactly two operands.) We will support +, - and * only (i.e. you do not need to implement division). Operands are always either numbers, or are variables. Any string which is not an integer or decimal number is to be considered a variable.

Infix notation (parenthesised)

Infix is the notation you are probably most familiar with. Infix notation puts binary operators (such as +, - and *) in between their operands, and uses parenthesis to show the order in which the operators should be applied. For this assignment we will strictly include the parenthesis even when we would normally omit them (for example, it’s obvious that 1 * 3 + 4 - 2 = 5, but your tree2infix method should write this as ((1 * 3) + (4 - 2))).

Prefix notation (without parenthesis)

Prefix notation is where the arithmetic operators are written before their operands. For example adding one and two in prefix notation is + 1 2. Because we know in advance how many operands each operator uses, we will never use parenthesis to show precedence (note: we are only using - as a binary operator. If we want to take the negative of a variable x, we’d have to write - 0 x to subtract it from zero.n

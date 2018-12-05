# Expression-Evaluation-Application
System takes input from the user in form of basic mathematical expressions

## Infix to Postfix conversion

#### Example : Infix Expression : (A+B/C*(D+E)-F)

| Symbol       | Stack          | Postfix                 |
| :---:        |     :---:      |   :---:                 |
| (            | (              |                         |
|      A       | (              |     A                   |
|      +       | ( +            |     A                   |
|      B       | ( +            |     A B                 |
|      /       | ( + /          |     A B                 |
|      C       | ( + /          |     A B C               |
|      *       | ( + *          |   A B  C /              |
|      (       | ( + * (        | A  B C /                |
| D            | ( + * (        | A B C / D               |
| +            | ( + * ( +      | A B C / D               |
| E            | ( + * ( +      |  A B C / D E            |
| )            | ( + *          | A B C / D E +           |
| -            | ( -            |A B C / D E + * +        |
| F            | ( -            |A B C / D E + * +  F     |
| )            |                |A B C / D E + * +  F -   |

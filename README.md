# csc413_project_5 #

 This is an Android application that interpret the FourFP language.

 It will allow a user to enter one statement at a time in a command line,

 which is then parsed, interpreted and executed to appropriate action.

##User Manual

####Available keywords:####

int, circle, rect, and clear

####Instruction:####

User shall type in one statement at a time which must end with a semicolon.

Minimum of one whitespace shall separate each lexeme.

User may use one of following three keywords in a single statment.

% 'int' - declare a variable and initialized to an integer literal

% 'circle' - draw circles, and 'rect' is used to draw rectangular shapes

% 'clear' or 'cls' - remove all outputs from the screen and variables from the memory

####Usage examples:####

int a = 2 ;

int b = a + 4 ;

int c = ( a * b ) ;

int d = c / 3 ;

circle x y r s

rect x1 y1 x2 y2 s

####Note:####

x, y, r, x1, y1, x2, y2 can be literal integers or pre-assigned variables

s is color code.
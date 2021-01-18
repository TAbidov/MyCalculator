import java.util.Scanner;


public class MyCalculator {

    static String ForCalc; // Введенная строка
    static int HaveBug=0;

    // 1111111111111111111111111111111111111
    public static boolean AllClearBug(){
              if (ForCalc.length()<=2)
                         {System.out.println("Некорректный ввод! Отсутствие примера"); HaveBug++; return false;}

             ReplaceRusXonEn(); //заменить все русские Х на английские Х
            if (!ClearStep1()) return false; //точки, запятые, отсутствие араб. и рим. цифр, выдать ошибку. false - некорректный ввод.
            if  (FindAnyErrSymbol()) return false; //если присутствуют запрещенные символы
            if  (!MathSymbolOne()) return false; // если символов мат операции больше одного
            if (!MathSymbolNotFirstNotEnd()) return false;  // +-*/ не в конце и не в начале
    return true;
    }
//  </  1111111111111111111111111111111111111


// 2222222222222222222222222222222222222
    // точки, запятые, пробелы, отсутствие мат.знака, отсутствие цифр либо присутствие цифр обоих систем исчисления
    private static boolean ClearStep1() {
        // отступы
        if (  ForCalc.indexOf(' ')>-1 ) {
             System.out.println("Неверный ввод. Отступы недопустимы "); HaveBug++; return false; }
        // точки запятые
        if (  ForCalc.indexOf('.')>-1 ||  ForCalc.indexOf(',')>-1 ) {
             System.out.println("Неверный ввод. Вещественные числа недопустимы "); HaveBug++; return false;}
        // если ни одного знака математической операции
        if (  (ForCalc.indexOf('+')==-1 &&  ForCalc.indexOf('-')==-1 && ForCalc.indexOf('*')==-1 && ForCalc.indexOf('/')==-1) ) {
             System.out.println("Отсутствует знак математической операции"); HaveBug++; return false; }

        //Если ни одной араб.цифры И ни одного римского знака, значит ошибочный ввод
        // 1) поиск араб цифр 0..9
        int FindedArab=0;
        int FindedRim=0;
        for (int i=0; i<10; i++) {
            FindedArab +=  ForCalc.indexOf(  Integer.toString(i) ) + 1;// +1 т.к. найденные начинаются с нуля, а отсутствие =-1;
        }//for
        //если FindedArab>0 значит араб.цифры находил

        // 2) поиск римских цифр
        FindedRim += ForCalc.indexOf('I') + 1;//+1 т.к неудача=-1
        FindedRim += ForCalc.indexOf('V') + 1;//+1 т.к неудача=-1
        FindedRim += ForCalc.indexOf('X') + 1;//+1 т.к неудача=-1
        //если Finded увеличился, значит римские фицры есть
        if (FindedArab==0 && FindedRim==0) {
             System.out.println("Ошибочный ввод! Арабские и римские цифры не обнаружены"); HaveBug++; return false;}
        if (FindedArab>0 && FindedRim>0) {
             System.out.println("Одновременное присутствие арабских и римских цифр запрещено"); HaveBug++; return false;}
        // < | поиск арабских и римских цифр>

    return true;
    }//ClearStep1
// </ 2222222222222222222222222222222222222




// 3333333333333333333333333333333333333333333
    // Поиск символов, не имеющих отношения к араб.цифрам, рим.цифрам и мат.операциям
    private static boolean FindAnyErrSymbol() {
        String OurSym = "IVX1234567890+-*/";
        int FindedError=-1;
        for (char c: ForCalc.toCharArray() ){
            FindedError = OurSym.indexOf(c); //нашел ли я этот символ среди допустимых
            if (FindedError==-1) {System.out.println("Ошибка! Найдены недопустимые символы!"); return true;}
        }//for
    return false;
    } // FindAnyErrSymbol()
// </ 3333333333333333333333333333333333333333333


// 44444444444444444444444444444444444444444444444
    // Заменить русскую Х на английский Х
    private static void ReplaceRusXonEn() {
        for (char c : ForCalc.toCharArray()) {
            if (ForCalc.indexOf('Х') > -1) {
                ForCalc = ForCalc.replace(c, 'X');
            } //if
        }//for
    }  //ReplaceRusXonEn()
// </ 44444444444444444444444444444444444444444444444


// 555555555555555555555555555555555555555555555
        //математические знаки не более одного
    private static boolean MathSymbolOne() {
  /*      S = ForCalc;
     for (int i=0; i<ForCalc.length(); i++) {
         char c = ForCalc.charAt(i);
     }
    */
    int CountMathSymbol=0;
        for (char c : ForCalc.toCharArray()) {
            if (c=='+' || c=='-' || c=='/' || c=='*') {
                CountMathSymbol++;
            }//if
        } //for
    if (CountMathSymbol>1) {
         System.out.println("Недопустимо количество символов математической операции более одного! ");
         return false;
         }//if
    return true;
    } //MathSymbolOne

// </555555555555555555555555555555555555555555

    //с это мат.символ ? ( + - * / )
    private static boolean itsMathSymbol(char c) {
      if (c=='+' || c=='-' || c=='*' || c=='/') {return true;}
      else return false;
    }

// 6666666666666666666666666666666666666666666
private static boolean MathSymbolNotFirstNotEnd() {
  /*      S = ForCalc;
     for (int i=0; i<ForCalc.length(); i++) {
         char c = ForCalc.charAt(i);
     }
    */
    if ( itsMathSymbol(ForCalc.charAt(0)) ||
         itsMathSymbol(ForCalc.charAt( ForCalc.length() - 1 ) ) ) {
        System.out.println("Недопустимо использовать математический знак вначале или в конце");
        return false;
    }//if
return true;
} //MathSymbolNotFirstNotEnd()

// </ 66666666666666666666666666666666666666

    // Данным методом мы поймем арабское ли число (в случае неудачи получим -1, а не конвертированное число) (готов)
    static int StrToInt(String s){
    /**/    Integer Result;
    /**/    try {
    /**/          Result = Integer.valueOf(s);
    /**/        }
    /**/    catch (NumberFormatException e) {
    /**/       //либо огромное число, либо не арабское (римское например
    /**/        return -1;
    /**/    }
    /**/
    /**/ return Result;
    } /*int IntToStr*/



    // Данный метод должен вернуть результат математической операции с римскими числами (готов)
    static String SumRim (String A, String B, char Arifmetic) {
   /**/               // 1    2      3     4     5    6      7      8      9    10
   /**/ String[] rim = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};
   /**/ int i = 0, j = 0, Result = 0, CountX = 0;
   /**/    String ResultRim = "";
   /**/
   /**/    //поиск римского аналога числа A
   /**/    while (!rim[i].equals(A)) {   //сравнение строк rim[i] и A
   /**/         i++;
   /**/    }//while
   /**/    i++; //т.к. i=4 если A=5
   /**/
   /**/    //поиск римского аналога числа B
   /**/    while ( !rim[j].equals(B)) {   //сравнение строк rim[i] и A
   /**/        j++;
   /**/    } //while
   /**/    j++; //j=4 если B=5 //поиск римского аналога
   /**/
   /**/    switch (Arifmetic) {
   /**/        case '+':
   /**/            Result = i + j;
   /**/            break;
   /**/        case '-':
   /**/            Result = i - j;
   /**/            break;
   /**/        case '*':
   /**/            Result = i * j;
   /**/            break;
   /**/        case '/':
   /**/            Result = i / j;
   /**/            break;
   /**/    }
   /**/
   /**/    // Теперь переведем число Result в римское
   /**/    CountX = Result / 10; // количество римских Х для десятков (запишется целое число, хоть и деление )
   /**/    for (i = 1; i <= CountX; i++) {
   /**/        ResultRim += 'X'; // XXX...
   /**/    }//for i
   /**/
   /**/    // В ResultRim набрали нужное количество X. Теперь добавим остаточные единицы
   /**/    i = Result - CountX*10; // 75-7*10=5  81-8*10=1   40-4*10=0
   /**/    if (i > 0) {
   /**/        ResultRim += rim[i-1];
   /**/    }
   /**/
        // не большое дополнение

        ResultRim = ResultRim.replace("XXXXXXXX", "LXXX");
        ResultRim = ResultRim.replace("XXXXXXX", "LXX");
        ResultRim = ResultRim.replace("XXXXXX", "LX");
        ResultRim = ResultRim.replace("XXXXX", "L");
        ResultRim = ResultRim.replace("XXXX", "XL");


        // </небольшое дополнение >
   /**/  return ResultRim;
   /**/  }



    public static void main(String[] args){
        System.out.println("Введите математический пример (к примеру А+В):");

        Scanner myVar = new Scanner(System.in);
        String CalcRead = myVar.nextLine(); // считываем ввод в одну строку
        CalcRead = CalcRead.toUpperCase(); //ForCalc = ForCalc.toUpperCase(); Вместо этой строки. В конструкторе жаловалась
        ForCalc = CalcRead;
        if ( !AllClearBug() ) {
             System.out.println("Программа прекращена!");
             return;}


        //тут до разбивки необходимо проделать чистку строки
       char[] chars = CalcRead.toCharArray(); // разбили посимвольно в массив [1][+][3]


       // получим А   * * * * * *
       /* A = getA_fromCharArray(); */
       /**/ String A="";
       /**/ int i=0;
       /**/ while (chars[i]!='+'  &&  chars[i]!='-'  &&  chars[i]!='*'  && chars[i]!='/') {
       /**/    A+=chars[i];
       /**/    i++;
       /**/  }
       //собрали А   * * * * * *


  // мы наткнулись на мат.знак +-*/  Запишем его тоже
        char Arifmetic = chars[i];  // i как раз стоит на нем

        // теперь соберем В, продолжая с позиции i+1 и до конца
        /**/   String B="";
        /**/   i++;
        /**/   while (i<chars.length) {
        /**/   B+=chars[i];
        /**/   i++;
        /**/   }
        // собрали В  * * * * * *


    System.out.println("Число А=" + A);
    System.out.println("Число B=" + B);
    System.out.println("Арифметический знак =" + Arifmetic);


    int NumA = StrToInt(A);
    int NumB = StrToInt(B);
    // если в арабские числа удалось перевести только одну переменную, то значит ввели арабские и римские(или другую хрень)
    if (   (NumA==-1 && NumB > 0 ) || (NumA>0 && NumB==-1)   ) {
        System.out.println("Некорректный ввод! Введите А и В в одном формате системы исчисления");
        }
    else if (NumA == 0 || NumB ==0) {
         System.out.println("Ошибка! Нуль недопустим! Завершение работы программы!");
         return;
        }
    else if (NumA > 10 || NumB > 10) {
        System.out.println("Ошибка! Числа больше десяти запрещены!");
        return;
    }
    // если оба числа оказались арабскими и выше нуля, то выполним указанную математическую операцию (+ - / *)
    else if (NumA>0 && NumB>0) {
               switch (Arifmetic) {
                   case '+': System.out.println(NumA+NumB);
                             break;
                   case '-': System.out.println(NumA-NumB);
                             break;
                   case '/': System.out.println(NumA/NumB);
                             break;
                   case '*': System.out.println(NumA*NumB);
               }//switch
    } //  < / else if (NumA>0 && NumB>0) >

    //Если же оба числа не удалось перевести в арабские, то это римские (при условии конечно, что мы профильтровали весь текст от ненужных символов
    else {
         System.out.println(  SumRim(A, B, Arifmetic )   );
         }//else

    }  /*main*/
} /*mycalculator*/






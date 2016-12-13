# LaTeX_Text_Justification

Consider  the problem of neatly printing a paragraph on a printer. The input
 * text  is a  sequence of  n words  of lengths l1;l2;...;ln, measured in characters. We want
 * to  print this  paragraph neatly  on a number of lines that hold a maximum of M characters
 * each.  Our criterion of “neatness” is as follows. If a given line contains words i through
 * j,  where i  < j,  and we leave exactly one space between words, the number of extra space
 * characters  at the  end of  the line  is M - j+ i-SUMMATION k from i to j (lk), which must
 * be  nonnegative so  that the  words fit on the line. We wish to minimize the sum, over all
 * lines  except the  last, of the cubes of the numbers of extra space characters at the ends
 * of lines. Give a dynamic-programming algorithm to print a paragraph of n words neatly on a
 * printer.  Analyze the  running time  and space  requirements of  your algorithm.
 *
 * Input    Instructions :    Can be    provided   either   through   console   or  via  run
 * configuration.  If provided  in command  line, the  input file  path is  of the  form, eg,
 * D://eclipse//workspace//file.txt and then the Line Width. If Run Configuration is used for
 * command line input use eg, D://eclipse//workspace//file.txt 132
 *  
 * Running Time : O(nM)
 
 

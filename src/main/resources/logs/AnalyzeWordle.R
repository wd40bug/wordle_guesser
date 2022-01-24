#Windows: "C:\\Users\\wd40b\\eclipse-workspace\\wordle guesser\\src\\main\\resources\\logs"
setwd("C:\\Users\\wd40b\\eclipse-workspace\\wordle guesser\\src\\main\\resources\\logs")
myData <- read.table("2022-01-20-19-37-37.psv",header=TRUE, sep="|")
library("stringr")
library(gcookbook)
library(tidyverse)
library(data.table)
library(dplyr)
myData$victory<-toupper(myData$victory)
myData$victory <- as.logical(myData$victory)

convert_to_vectors <- function(data){
  foo <- list()
  for(val in data) {
    x <- strsplit(val,",")
    foo<-append(foo,x)
  }
  foo
}

myData$values <- convert_to_vectors(myData$values)
myData$values <- lapply(myData$values,as.integer)
myData$guesses<- convert_to_vectors(myData$guesses)



Guesses <- table(myData$rounds)
Guesses_vector <- 0
for (var in Guesses) {
  Guesses_vector <- append(Guesses_vector, var)
}
guess_distribution_plot <- barplot(
  Guesses_vector,
  col = "paleturquoise2",
  border = "white",
  names = 1:6,
  xlab = "Guesses",
  ylab = "Successes",
  ylim = c(0, 800),
  main = "Distribution of winning guesses by round"
)
text(guess_distribution_plot, 0, round(Guesses_vector, 1), cex = 1, pos = 3)

has_double_letters <- function(word) {
  numberstring_split <- strsplit(word, "")[[1]]
  for (cha in numberstring_split) {
    if (nchar(str_remove_all(word, cha)) < (nchar(word) - 1)) {
      return(T)
    }
  }
  F
}

failures <- 0
double_word_failures <- 0
double_letter_words <- 0
success_row_numbers <- NULL
for (i in seq_along(myData$victory)) {
  if (myData[i, 3] == F) {
    failures <- failures + 1
    if (has_double_letters(myData[i, 1])) {
      double_word_failures <- double_word_failures + 1
    }
  }else{
    success_row_numbers <- append(success_row_numbers,i)
  }
  if (has_double_letters(myData[i, 1])) {
    double_letter_words <- double_letter_words + 1
  }
}
head(myData[myData$victory==F,])
answer_value_hist<-hist(
  myData$answerValue,
  main = "Values of Mystery Words",
  xlab = "value",
)
text(answer_value_hist$mids,answer_value_hist$counts,labels = answer_value_hist$counts, adj=c(0.5, -0.5))
double_letter_failure_plot <- barplot(
  c(double_word_failures, (failures - double_word_failures))
  , ylim = c(0, 100),
  col = "deep sky blue",
  names = c("Words With Double Letters", "Words Without Double Letters"),
  xlab = "Type Of Word",
  ylab = "Number Of Failures",
  main = "Failures by Whether or Not the Word had a Double Letter"
)
text(double_letter_failure_plot, 0, round(c(double_word_failures,
                                            (failures - double_word_failures)))
  , cex = 1, pos = 3)
double_letter_word_percent <- double_letter_words / length(myData$answer)

normalized_double_letter_word_vector <- c((double_word_failures / double_letter_words)*100,
                                          ((failures - double_word_failures) /
                                            (length(myData$answer)-double_letter_words))*100)

double_letter_failure_plot_normalized <- barplot(
  normalized_double_letter_word_vector,
  ylim = c(0,16),
  col = "dark slategray 2",
  names = c("Words With Double Letters", "Words Without Double Letters"),
  xlab = "Type Of Word",
  ylab = "Likelyhood of failure",
  main = "Likelyhood of failure for different types of words"
)
text(double_letter_failure_plot, 0, round(normalized_double_letter_word_vector)
  , cex = 1, pos = 3)
foo<-sapply(myData$values,mean)
boxplot(
  myData$answerValue,
  horizontal = T,
  main="Distribution of guess values",
  xlab = "Value"
)

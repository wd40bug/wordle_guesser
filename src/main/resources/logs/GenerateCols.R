# Generate a plot of color names which R knows about.
#++++++++++++++++++++++++++++++++++++++++++++
# cl : a vector of colors to plots
# bg: background of the plot
# rot: text rotation angle
#usage=showCols(bg="gray33")
showCols <- function(cl=colors(), bg = "grey",
                     cex = 0.75, rot = 30) {
  m <- ceiling(sqrt(n <-length(cl)))
  length(cl) <- m*m; cm <- matrix(cl, m)
  require("grid")
  grid.newpage(); vp <- viewport(w = .92, h = .92)
  grid.rect(gp=gpar(fill=bg))
  grid.text(cm, x = col(cm)/m, y = rev(row(cm))/m, rot = rot,
            vp=vp, gp=gpar(cex = cex, col = cm))
}
showCols(cl= colors(), bg="gray33", rot=30, cex=0.75)
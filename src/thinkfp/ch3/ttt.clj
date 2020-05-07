(ns thinkfp.ch3.ttt
  (:require [thinkfp.ch3.draw :as dw]
            [thinkfp.ch3.cell :as cl]
            [thinkfp.ch3.grid :as gd]
            [thinkfp.utils :as ut]
            [clojure.pprint :as pp]))

;; ----------------------------------------------------------------------------
;; Setting up initial board.

(def mk-empty (cl/make-cell-with-materials "+" "|" "-"))
(def E (mk-empty [" "]))

(defn create-empty-board [r c]
  ((gd/make-grid-with-cell E) r c))

;; ----------------------------------------------------------------------------
;; Functions helping for making a move.

(defn empty-cell? [cell] 
  "Check whether the cell is an empty cell, E"
  (= cell E))

;; Get empty cells where a move can be made.
(defn get-available-cells [board]
  "Get all empty cells where a move can be made"
  (for [[r row] (map-indexed vector board)
        [c cell] (map-indexed vector row)
        :when (empty-cell? cell)]
    [r c]))

;; Modify the cell's value.
(defn move [board pos player]
  (assoc-in board pos player))

;; Makes a move randomly on an empty cell.
(defn make-a-move [board player]
  (let [available (get-available-cells board)
        cell-pos (rand-nth available)]
    (move (ut/vectorize-2d board) cell-pos player)))

;; ----------------------------------------------------------------------------
;; Checking game status.

;; When a move is made, determine the status of the game.
;; Possible statuses: WIN/DRAW/UNFINISHED

(defn game-over? [board]
  (empty? (get-available-cells board)))

;; Check for winner in each row. Columnwise and diagonal check can be done 
;; by first transforming them to rows. 
(defn row-wise-winner [board]
  "Checks each row to determine the winner.
  Returns nil if there is no winner."
  (letfn [(won? [row] 
            (and (apply = row) (not= (first row) E)))
          (who-won? [row] 
            (if (won? row) 
              (first row)))]

    (loop [bd board]
      (when-not (empty? bd)
        (let [row (first bd)
              victor (who-won? row)]
          (if victor
            victor
            (recur (rest bd))))))))

(defn col-wise-winner [board]
  (let [transposed (ut/transpose board)]
    (row-wise-winner transposed)))

(defn diagonal-wise-winner [board]
  (let [diags (ut/diagonals board)]
    (row-wise-winner diags)))

;; Check for the winner. Returns :draw if the game is drawn.
;; First check rowwise, then columnwise and then diagonals.
(defn find-winner [board]
  "Finds the winner if there is. Else :draw. 
  Returns nil if neither ie, game not finished."
  (or 
    (row-wise-winner board)
    (col-wise-winner board)
    (diagonal-wise-winner board)
    (when (game-over? board) :draw)))

;; ----------------------------------------------------------------------------
;; Game play.  
;; At each turn select one of the empty cells and make the move with alternate
;; players. Select the cells randomly. No AI!. At each turn, check the status
;; of the game, and if finished return the winner or :draw.

;; This has side effects, at each turn draws the board.
(defn play [board players]
  "Play tic-tac-toe with the board and players"
  (loop [updated board
         turns (cycle players)] 
    (let [player (first turns)
          victor (find-winner updated)]
      (if victor 
        (do (dw/draw-grid updated) ; fin
            victor)
        (do (dw/draw-grid updated) ; continue
            (println "              ")
            (recur (make-a-move updated player)
                   (rest turns)))))))

;; ----------------------------------------------------------------------------
(defn play-a-game []
  ; Setup players and board
  (def make-x (cl/make-cell-with-materials "+" "|" "-"))
  (def make-o (cl/make-cell-with-materials "+" "|" "-"))
  (def X (make-x ["X"]))
  (def O (make-o ["0"]))

  (def board (create-empty-board 3 3))

  ; Play.
  (let [result (play board [X O])]
    (if (= result :draw)
      (println "DRAW")
      (do (println "And the winner is...")
          (dw/draw-cell result :top true)))))

(play-a-game)

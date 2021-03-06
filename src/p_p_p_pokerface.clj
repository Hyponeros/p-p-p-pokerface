(ns p-p-p-pokerface)



(defn rank [card]
  (let [rank (first card)
        figures {\T 10 \J 11 \Q 12 \K 13 \A 14}]
    (if (Character/isDigit rank)
      (Integer/valueOf (str rank))
      (figures rank))))

(defn suit [card]
  (str (second card)))

(defn hand-freqs [hand]
  (vals (frequencies (map rank hand))))

(defn max-freq? [hand val]
  (if (>= (apply max (hand-freqs hand)) val)
    true
    false))

(defn pair? [hand]
 (max-freq? hand 2))

(defn three-of-a-kind? [hand]
  (max-freq? hand 3))

(defn four-of-a-kind? [hand]
  (max-freq? hand 4))

(defn flush? [hand]
  (apply = (map suit hand)))

(defn full-house? [hand]
    (= [2 3] (sort (hand-freqs hand))))

(defn two-pairs? [hand]
  (= [1 2 2] (sort (hand-freqs hand))))

(defn straight? [hand]
  (let [values (sort (map rank hand))
        alt-values (sort (replace {14 1} values))
        start (apply min values)]
    (cond 
      (= (range start (+ start 5)) values) true
      (= (range 1 6) alt-values) true
      :else false)))

(defn straight-flush? [hand]
  (and (straight? hand)
       (flush? hand)))

(defn high-card [hand]
  true)

(defn value [hand]
  (cond
    (straight-flush? hand) 8
    (four-of-a-kind? hand) 7
    (full-house? hand) 6
    (flush? hand) 5
    (straight? hand) 4
    (three-of-a-kind? hand) 3
    (two-pairs? hand) 2
    (pair? hand) 1   
    :else 0))    

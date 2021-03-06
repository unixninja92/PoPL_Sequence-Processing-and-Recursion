(ns HW4.core)


;Problem 1
(defn create-freq-map
  [file]
  (frequencies 
    (reduce conj '() 
            (map first 
                 (map clojure.string/lower-case 
                      (clojure.string/split-lines 
                        (slurp file)))))))


(create-freq-map "/usr/share/dict/words")


;Problem 2

;a
(defn remdup
  [l]
  (loop 
    [pos 0
     npos 0
     nl '()]
    (if (< pos (dec (count l)))
      (if (= (nth l pos) (nth l (inc pos)))
        (recur (inc pos) npos nl)
        (recur (inc pos) (inc npos) (conj nl (nth l pos))))
      (if (= pos (dec (count l)))
        (recur (inc pos) (inc npos) (conj nl (nth l pos)))
        (reverse nl)))))
    
(remdup '(:a :b :a :a :a :c :c))


;b
(defn nested-remdup
  [e]
  (if (list? e)
    (loop 
      [pos 0
       l e
       npos 0
       nl '()
       linl '()]
      (if (< pos (dec (count l)))
        (if (and (list? (nth l pos)) (= (.indexOf linl pos) -1))
          (recur pos (concat (take pos l)(list (nested-remdup (nth l pos)))(drop (inc pos) l)) npos nl (conj linl pos))
          (if (= (nth l pos) (nested-remdup (nth l (inc pos))))
              (recur (inc pos) l npos nl linl)
              (recur (inc pos) l (inc npos) (conj nl (nth l pos)) linl)))
        (if (= pos (dec (count l)))
            (recur (inc pos) l (inc npos) (conj nl (nth l pos)) linl)
          (reverse nl))))
    e))

 (nested-remdup '(:a :a (:a :a :a) (:a :a :a) :c :c :d :c ((:d :d :a) :a) :a))


;Problem 3

;a
(defn prime? [num]
  (not-any? #(= (rem num %) 0) 
       (range 2 (inc(Math/sqrt num)))))

(defn next-primes
  [l]
  (map (fn [n]
         (if (prime? n)
           n
           (loop
             [high (inc n)]
             (if (prime? high)
                 high
               (recur (inc high))))))
       l))

(next-primes '(4 7 34 100 2990 3002))


;b
(defn lazy-primes
  ([]
    (lazy-primes 2 (lazy-seq)))
  ([num coll]
    (let [step (fn [n primes]
                   (if (not-any? #(zero? (rem n %))
                                 (filter #(<= % (Math/sqrt n)) 
                                         primes))
                     (let [new-primes (lazy-cat (list n) primes)]
                       (cons n (lazy-primes (inc n) new-primes)))
                       (lazy-primes (inc n) primes)))]
      (lazy-seq (step num coll)))))

(take 20 (lazy-primes))


;Problem 4
(defn collatz-seq-len
  ([x]
    (collatz-seq-len x 0))
  ([x n]
  (if (= x 1)
    n
    (if (even? x)
      (collatz-seq-len (/ x 2) (inc n))
      (collatz-seq-len (+ 1 (* 3 x))(inc n))))))
    

(defn longest-collatz-seq
  [x y]
  (let
    [ran (range x (inc y))
     lens (map collatz-seq-len ran)
     max (apply max lens)
     pos (.indexOf lens max)]
    (nth ran pos)))


(longest-collatz-seq 2 10)


;Problem 5 

(defn x-to-10
  "Converts a character to it's numeric value and the char 'X' to 10"
  [c]
  (first (replace {33 10} (list (Character/getNumericValue c)))))

(defn strike
  [sc]
  (let 
    [pos3 (nth sc 2)]
    (if (= pos3 \/)
      20
      (apply + (take 3 (map x-to-10 sc))))))

(defn spare 
  [sc]
  (+ 10 (x-to-10 (nth sc 2))))
  

(defn ten-pin-score
  ([sc]
    (ten-pin-score (replace {\- \0} (seq sc)) 1 0))
  ([sc frame score]
;    (println sc " frame: " frame)
    (if (> frame 10)
      score
      (if (= (first sc) \X)
        (ten-pin-score (rest sc)
                       (inc frame)
                       (+ score (strike sc)))
        (if(= (second sc) \/)
              (ten-pin-score (drop 2 sc)
                             (inc frame)
                             (+ score (spare sc)))
              (ten-pin-score 
                (drop 2 sc) 
                (inc frame) 
                (+ score 
                   (Character/getNumericValue (first sc))
                   (Character/getNumericValue (second sc)))))))))

(ten-pin-score "--3---2----1-8-11---") ;16

(ten-pin-score "6/34----------------") ;20

(ten-pin-score "----XX3-----------") ;39

(ten-pin-score "726/-8-69/6/33456252") ;92

(ten-pin-score "X52X52X52X52X52") ;120

(ten-pin-score "X7/729/XXX236/7/3") ;168

(ten-pin-score "XXXXX6/XX7/XX5") ;248
  
(defn -main
  [& args]
  )

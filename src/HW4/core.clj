(ns HW4.core
  (require clojure.string clojure.tools.trace))


;Problem 1
(defn create-freq-map
  [file]
  (frequencies 
    (reduce conj '() 
            (map first 
                 (map clojure.string/lower-case 
                      (clojure.string/split-lines 
                        (slurp file)))))))


;(create-freq-map "/usr/share/dict/words")

;(clojure.tools.trace/dotrace [create-freq-map] (create-freq-map "/usr/share/dict/words"))


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
    
;(remdup '(:a :b :a :a :a :c :c))


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

; (nested-remdup '(:a :a (:a :a :a) (:a :a :a) :c :c :d :c ((:d :d :a) :a) :a))


;Problem 3

;b

(defn lazy-primes ;;lazy-cat
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

;a
(defn next-primes
  [l]
  
  )



(defn -main
  [& args]
  )

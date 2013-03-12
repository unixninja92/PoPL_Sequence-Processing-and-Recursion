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


(create-freq-map "/usr/share/dict/words")

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

;b
(let 
  [primes []]
  (map (fn 
         [n]
         (if (not-any? (filter #(zero? (rem n %)) primes)
                 (filter #(<= % (Math/sqrt n)) primes))
           (lazy-cat primes n)
           primes))
       (iterate inc 2)))
(defn lazy-primes ;;lazy-cat
  []
  (for
    [laz (list)
     n 2]
    (if (and
          (= (count (filter zero? (map #(rem n %) laz))) 0)
          (= (count (filter #(<= % (Math/sqrt n)) laz)) 0))
      (recur (lazy-cat laz n) (inc n))
      (recur laz (inc n)))))

(take 10 lazy-primes)
;a
(defn next-primes
  [l]
  
  )



(defn -main
  [& args]
  )

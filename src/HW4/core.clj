(ns HW4.core
  (require clojure.string clojure.tools.trace)
  (:import (java.io BufferedReader FileReader)))


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
       nl '()]
      (let
        [len (count l)
         item (nth l pos)]
        (if (< pos (dec len))
          (if (list? item)
            (if (not= (nested-remdup item) item)
              (recur pos (concat (take pos l)(list (nested-remdup item))(drop (inc pos) l)) npos nl))
            (if (= item (nth l (inc pos)))
              (recur (inc pos) l npos nl)
              (recur (inc pos) l (inc npos) (conj nl item))))
          (if (= pos (dec len))
            (recur (inc pos) l (inc npos) (conj nl item))
            (reverse nl)))))
    e))

; (nested-remdup '(:a :a (:a :a :a) (:a :a :a) :c :c :d :c ((:d :d :a) :a) :a))


;Problem 3

(defn next-primes
  [l]
  )



(defn -main
  [& args]
  )

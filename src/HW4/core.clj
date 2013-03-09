(ns HW4.core
  (require clojure.string)
  (:import (java.io BufferedReader FileReader)))


;Problem 1
(def freq-map {\a 0, \b 0, \c 0, \d 0, \e 0, \f 0, \g 0, \h 0, \i 0, \j 0, \k 0, \l 0, \m 0, \n 0, \o 0, \p 0, \q 0, \r 0, \s 0, \t 0, \u 0, \v 0, \w 0, \y 0, \x 0, \z 0})

(def file "/usr/share/dict/words")
(def reader (line-seq (BufferedReader. (FileReader. file))))
(def reader-len (count reader))


(defn read-file-line
  [freq-map line-num] 
  (if (not= line-num reader-len)
    (let 
      [fchar (first (clojure.string/lower-case (do (nth reader line-num))))]
;      (println (get freq-map fchar))
        (read-file-line (assoc freq-map fchar (inc (get freq-map fchar))) (inc line-num)))))

(read-file-line freq-map 0)


;Problem 2

;(defn remdup
;  [l]
;  (loop 
;    [pos 0
;     npos 0
;     nl '()]
;    (if (< pos (dec (count l)))
;      (if (= (nth l pos) (nth l (inc pos)))
;        (recur (inc pos) npos nl)
;        (recur (inc pos) (inc npos) (conj nl (nth l pos))))
;      (if (= pos (dec (count l)))
;        (recur (inc pos) (inc npos) (conj nl (nth l pos)))
;        (reverse nl))))) 
    
;(remdup '(:a :b :a :a :a :c :c))


;Problem 3





(defn -main
  [& args]
  )

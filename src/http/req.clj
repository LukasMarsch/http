(ns http.req
  (:require clojure.string)
  (:require log))

(def logger (log/init "seq" :debug))

(defn req
  ([] nil)
  ([req] (let [field (clojure.string/split
                       (nth (clojure.string/split req #"\r\n") 0)
                       #" ")]
           ((logger :debug) req)
    {
      :method (nth field 0)
      :path (nth field 1)
      :version (nth field 2)
    })))

(ns add-to-set-test.core
  (:require [monger.core :as mg]
            [monger.db :as md]
            [monger.collection :as mc]))

(def coll "add-to-set")

(defn -main []
  (let [conn (mg/connect)
        db (mg/get-db conn "foo")]
    (mc/insert db coll
               {:id "foo"
                :data #{}})
    (println "Adding 10000 to set")
    (time (mc/update db coll
                     {:id "foo"}
                     {"$addToSet" {:data (into #{} (range 10000))}}))
    (println "Adding 100,000 to set")
    (time (mc/update db coll
                     {:id "foo"}
                     {"$addToSet" {:data (into #{} (range 100000))}}))
    (println "Adding 1,000,000 to set")
    (time (mc/update db coll
                     {:id "foo"}
                     {"$addToSet" {:data (into #{} (range 1000000))}}))
    (md/drop-db db)))

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
    (println "Adding 500,000 to set")
    (time (mc/update db coll
                     {:id "foo"}
                     {"$addToSet" {:data (into #{} (range 500000))}}))
    (println "Adding additional [500,000 - 750,000] to set")
    (time (mc/update db coll
                     {:id "foo"}
                     {"$addToSet" {:data (into #{} (range 500000 750000))}}))
    (println "Adding additional [1,000,000 - 1,250,000] to set")
    (time (mc/update db coll
                     {:id "foo"}
                     {"$addToSet" {:data (into #{} (range 1000000 1250000))}}))
    (md/drop-db db)))

(ns cms.domain.author
  (:require [datomic.api :as api]
            [cms.datomic.core :as db]
            [cms.domain.article :as ar]))

(defn create [name email]
  (->> {:author/name name
        :author/email email
        :db/id (api/tempid :db.part/user)}
       vector
       (api/transact db/CONN)
       deref))

(defn find-all []
  (->> (api/q '[:find ?e :where [?e :author/email]] (api/db db/CONN))
       (map #(api/entity (api/db db/CONN) (first %)))
       (map api/touch)))

(defn find-by-email [email]
  (->> (api/q '[:find ?e :in $ ?email :where [?e :author/email ?email]] (api/db db/CONN) email)
       (map #(api/entity (api/db db/CONN) (first %)))
       (map api/touch)
       first))

(defn update [a name]
  (->> [:db/add (:db/id a) :author/name name]
       vector
       (api/transact db/CONN)
       deref))

(defn delete [a]
  (->> [:db.fn/retractEntity (:db/id a)]
       vector
       (api/transact db/CONN)
       deref))

(defn add-article [author title body category]
  (let [a (ar/create title body category)]
    (->> [:db/add (:db/id author) :author/articles (:db/id a)]
         vector
         (api/transact db/CONN)
         deref)))
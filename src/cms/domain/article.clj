(ns cms.domain.article
  (:require [datomic.api :as api]
            [cms.datomic.core :as db]
            [cms.domain.tag :as tag]))

(defn create [title body category]
  (let [t (api/tempid :db.part/user)
        a {:article/title title
           :article/body body
           :article/category category
           :db/id t}
        {:keys [db-after tempids]} (->> a
                                        vector
                                        (api/transact db/CONN)
                                        deref)
        ar-id (api/resolve-tempid db-after tempids t)]
    (->> ar-id
         (api/entity db-after)
         api/touch)))

(defn find-all []
  (->> (api/q '[:find ?e :where [?e :article/title]] (api/db db/CONN))
       (map #(api/entity (api/db db/CONN) (first %)))
       (map api/touch)))

(defn add-tag [ar tag-name]
  (let [t (tag/create tag-name)]
    (->> [:db/add (:db/id ar) :article/tags (:db/id t)]
         vector
         (api/transact db/CONN)
         deref)))

(ns health-check.views.monitoring-page
  (:require [health-check.subs :as subs]
            [health-check.views.nav :as nav]
            [re-frame.core :as re-frame]))

(defn- calc-font-size [row-height severity]
  (str (* row-height 10) "%"))

(def powers-of-two
  (iterate (partial * 2) 1))

(defn width-based-of-severity [severity]
  (nth powers-of-two (dec severity)))

(defn- unhealthy-service [{:keys [severity
                                  row-height
                                  env
                                  service-name]}]
  [:div.service-container
   {:class (str "pure-u-1-" (width-based-of-severity severity))
    :style {:height (str row-height "%")}}
   [:div.unhealthy-service
    [:span.name-container
     [:p.service-name
      {:style {:fontSize (calc-font-size row-height severity)}}
      (str env " " service-name)]]]])

(defn- calc-row-height [unhealthy-services]
  (let [total-rows (Math/ceil
                    (apply + (map (fn [x] (/ 1 (:severity x)))
                                  unhealthy-services)))
        row-height (/ 100 total-rows)]
    (map #(assoc % :row-height row-height) unhealthy-services)))

(defn- health-map [severice-health-states]
  (if (> (count severice-health-states) 0)
    [:div.health-map.pure-g
     (->> (vals severice-health-states)
          (remove :healthy?)
          (sort-by :severity)
          calc-row-height
          (map (fn [service] ^{:key service} [unhealthy-service service])))]
    [:h2 "Healthy! :-)"]))

(defn- health-summaries []
  (let [service-health-states (re-frame/subscribe [::subs/health-check-results])]
    [:div.health-summaries
     [health-map  @service-health-states]]))

(defn monitoring-panel []
  [:div
   [nav/navigation-bar]
   [health-summaries]])

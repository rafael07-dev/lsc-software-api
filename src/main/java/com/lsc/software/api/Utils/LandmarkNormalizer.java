package com.lsc.software.api.Utils;

import java.util.ArrayList;
import java.util.List;

public class LandmarkNormalizer {

    public static List<List<Double>> normalizeFrame(List<List<Double>> frame) {
        List<Double> center = frame.get(0); // landmark 0 - muñeca

        List<List<Double>> normalized = new ArrayList<>();
        for (List<Double> point : frame) {
            double x = point.get(0) - center.get(0);
            double y = point.get(1) - center.get(1);
            double z = point.get(2) - center.get(2);
            normalized.add(List.of(x, y, z));
        }

        double maxDistance = normalized.stream()
                .mapToDouble(p -> Math.sqrt(p.get(0) * p.get(0) + p.get(1) * p.get(1) + p.get(2) * p.get(2)))
                .max()
                .orElse(1.0); // evitar división por 0

        List<List<Double>> result = new ArrayList<>();
        for (List<Double> point : normalized) {
            result.add(List.of(
                    point.get(0) / maxDistance,
                    point.get(1) / maxDistance,
                    point.get(2) / maxDistance
            ));
        }

        return result;
    }

    public static List<List<List<Double>>> normalizeSequence(List<List<List<Double>>> sequence) {
        List<List<List<Double>>> normalizedSequence = new ArrayList<>();
        for (List<List<Double>> frame : sequence) {
            normalizedSequence.add(normalizeFrame(frame));
        }
        return normalizedSequence;
    }
}

package aoc22

object Day10 {

    sealed interface Instruction {
        val cycleLength: Int

        object NoOp : Instruction {
            override val cycleLength get() = 1
        }

        data class AddX(val increment: Int) : Instruction {
            override val cycleLength: Int get() = 2
        }
    }

    data class State(val x: Int = 1, val cycle: Int = 1) {
        fun nextCycle() = State(x, cycle + 1)
        fun incRegister(value: Int) = State(x + value, cycle)
        fun signalStrength() = x * cycle
        fun crtOutput(): Char {
            val crtPos = ((cycle - 1) % 40)
            return if (crtPos in (x - 1)..(x + 1)) '▓' else '░'
        }
    }

    fun calcSignalStrengthSum(input: String): Int {
        val lines = input.trimEnd().lines()
        val instructions = parseInstructions(lines)
        val states = run(listOf(State()), instructions.first(), 1, instructions.drop(1))
        val selected = listOf(states[19], states[59], states[99], states[139], states[179], states[219])
        return selected.sumOf { it.signalStrength() }
    }

    fun calcCrtLines(input: String): List<String> {
        val lines = input.trimEnd().lines()
        val instructions = parseInstructions(lines)
        val states = run(listOf(State()), instructions.first(), 1, instructions.drop(1))
        return states.map { it.crtOutput() }.chunked(40).map { it.joinToString(separator = "") }
    }

    private tailrec fun run(
        states: List<State>,
        currentInstruction: Instruction,
        currentInstructionCycle: Int,
        remaining: List<Instruction>,
    ): List<State> {
        if (remaining.isEmpty() && currentInstructionCycle == currentInstruction.cycleLength) {
            return states
        }
        return if (currentInstructionCycle < currentInstruction.cycleLength) {
            val newState = states.last().nextCycle()
            run(states + newState, currentInstruction, currentInstructionCycle + 1, remaining)
        } else {
            val nextCycleState = states.last().nextCycle()
            when (currentInstruction) {
                is Instruction.NoOp -> run(states + nextCycleState, remaining.first(), 1, remaining.drop(1))
                is Instruction.AddX -> {
                    val newState = nextCycleState.incRegister(currentInstruction.increment)
                    run(states + newState, remaining.first(), 1, remaining.drop(1))
                }
            }
        }
    }

    private fun parseInstructions(lines: List<String>): List<Instruction> = lines.map { line ->
            if (line == "noop") Instruction.NoOp else Instruction.AddX(line.substringAfter(' ').toInt())
        }
}